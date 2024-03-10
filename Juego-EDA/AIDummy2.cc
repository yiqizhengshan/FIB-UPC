#include "Player.hh"

/**
 * Write the name of your player and save this file
 * with the same name and .cc extension.
 */
#define PLAYER_NAME Dummy2

struct PLAYER_NAME : public Player
{

    /**
     * Factory: returns a new instance of this class.
     * Do not modify this function.
     */
    static Player *factory()
    {
        return new PLAYER_NAME;
    }
    /**
     * Types and attributes for your player can be defined here.
     */
    const vector<Dir> dire = {Down, Right, Up, Left};
    const vector<Dir> dire_zomb = {DR, RU, UL, LD};
    typedef pair<int, pair<int, Dir>> colita;

    //devuelve true si en un Pos te puede matar un zombie
    bool hay_zombie(Pos posicion) {
        Pos new_pos = posicion;
        for (int i = 0; i < 4; ++i) {
            Pos otro_pos = new_pos + dire_zomb[i];
            if (pos_ok(otro_pos)) {
                if (cell(otro_pos).id >= 0 and unit(cell(otro_pos).id).type == Zombie) return true;
            }
        }
        return false;
    }
    // funcion que asigna a cada comida el jugador mas cercano tuyo y la distancia para la priority queue
    void asignar_comida(Pos pos, int n, int m, map<int, pair<int, Dir>>& comida) {
        queue<Pos> Cola;
        Cola.push(pos);
        vector<vector<bool>> enc (n, vector<bool>(m, false));
        enc[pos.i][pos.j] = true;
        int distancia = 0;

        while (not Cola.empty()) {
            Pos act = Cola.front();
            Cola.pop();
            ++distancia;
            for (int i = 0; i < 4; ++i) {
                Dir direc = dire[i];
                Pos new_pos = act + direc;
                if (pos_ok(new_pos) and cell(new_pos).type != Waste and not enc[new_pos.i][new_pos.j]) {
                        int dni = cell(new_pos.i, new_pos.j).id;

                        if (dni >= 0 and unit(dni).type == Alive and unit(dni).player == me()) {
                            auto it = comida.find(dni);
                            if (it == comida.end()) comida.insert({dni, {distancia, dire[(i + 2)%4]}});
                            else {
                                int dist1 = (*it).second.first;
                                if (distancia < dist1) it->second = {distancia, dire[(i + 2)%4]};
                            }
                            return;
                        }

                    Cola.push(new_pos);
                    enc[new_pos.i][new_pos.j] = true;
                }
            }
        }
    }
    // devuelve Dirección que hay que seguir, distancia a la que está y Posición una vez hecho el movimiento y Posicion de la persona
    pair<Dir, pair<int, pair<Pos, Pos>>> matar_gente(int id, Pos pos, int n, int m) {
        queue<Pos> Cola;
        Cola.push(pos);
        vector<vector<bool>> enc (n, vector<bool>(m, false));
        map<Pos, pair<Pos, Dir>> mapa;
        bool trobat = false;
        Pos sol;
        enc[pos.i][pos.j] = true;

        while (not Cola.empty()) {
            Pos act = Cola.front();
            Cola.pop();
            for (int i = 0; i < 4; ++i) {
                Dir direc = dire[i];
                Pos new_pos = act + direc;
                if (pos_ok(new_pos) and cell(new_pos).type != Waste and
                    not enc[new_pos.i][new_pos.j]) {
                        int dni = cell(new_pos.i, new_pos.j).id;
                        if (dni >= 0 and unit(dni).type == Alive and unit(dni).player != me()) {
                            trobat = true;
                            sol = new_pos;
                        }
                    mapa.insert({new_pos, {act, direc}});
                    Cola.push(new_pos);
                    enc[new_pos.i][new_pos.j] = true;
                }
                if (trobat) {
                    break;
                }
            }
            if (trobat) {
                break;
            }
        }
        if (trobat) {
            int dist = 1;
            Pos dir = sol;
            while (dir != pos) {
                auto it = mapa.find(dir);
                if ((it->second).first == pos) {
                    Dir hacer = (it->second).second;
                    return make_pair(hacer, make_pair(dist, make_pair(it->first, sol)));
                }
                ++dist;
                dir = (it->second).first;
            }
        }
        return make_pair(DR, make_pair(-1, make_pair(pos, pos)));
    }
    // devuelve Dirección que hay que seguir, distancia a la que está y Posición una vez hecho el movimiento y Posicion del zombie
    pair<Dir, pair<int, pair<Pos, Pos>>> matar_zombie(int id, Pos pos, int n, int m) {
        queue<Pos> Cola;
        Cola.push(pos);
        vector<vector<bool>> enc (n, vector<bool>(m, false));
        map<Pos, pair<Pos, Dir>> mapa;
        bool trobat = false;
        Pos sol;
        enc[pos.i][pos.j] = true;

        while (not Cola.empty()) {
            Pos act = Cola.front();
            Cola.pop();
            for (int i = 0; i < 4; ++i) {
                Dir direc = dire[i];
                Pos new_pos = act + direc;
                if (pos_ok(new_pos) and cell(new_pos).type != Waste and
                    not enc[new_pos.i][new_pos.j]) {
                        int dni = cell(new_pos.i, new_pos.j).id;
                        if (dni >= 0 and unit(dni).type == Zombie) {
                            trobat = true;
                            sol = new_pos;
                        }
                    mapa.insert({new_pos, {act, direc}});
                    Cola.push(new_pos);
                    enc[new_pos.i][new_pos.j] = true;
                }
                if (trobat) {
                    break;
                }
            }
            if (trobat) {
                break;
            }
        }
        if (trobat) {
            int dist = 1;
            Pos dir = sol;
            while (dir != pos) {
                auto it = mapa.find(dir);
                if ((it->second).first == pos) {
                    Dir hacer = (it->second).second;
                    return make_pair(hacer, make_pair(dist, make_pair(it->first, sol)));
                }
                ++dist;
                dir = (it->second).first;
            }
        }
        return make_pair(DR, make_pair(-1, make_pair(pos, pos)));
    }
    // devuelve un vector con la Posicion de todas las comidas que hay en el mapa
    vector<Pos> pos_de_comida(int n, int m, int total) {
        vector<Pos> v(total);
        int c = 0;
        for (int i = 0; i < n and c < total; ++i) {
            for (int j = 0; j < m and c < total; ++j) {
                if (cell(i ,j).food) {
                    v[c] = Pos(i, j);
                    ++c;
                }
            }
        }
        return v;
    }
    // BFS que mete en la priority queue un movimiento para conquistar territorio
    void conquistar_terrritorio(priority_queue<colita, vector<colita>, greater<colita>>& movimientos, int id, Pos pos, int n, int m) {
        queue<Pos> Cola;
        Cola.push(pos);
        vector<vector<bool>> enc (n, vector<bool>(m, false));
        map<Pos, pair<Pos, Dir>> mapa;
        bool trobat = false;
        Pos sol;
        enc[pos.i][pos.j] = true;

        while (not Cola.empty()) {
            Pos act = Cola.front();
            Cola.pop();
            for (int i = 0; i < 4; ++i) {
                Dir direc = dire[i];
                Pos new_pos = act + direc;
                if (pos_ok(new_pos) and cell(new_pos).type != Waste and
                    not enc[new_pos.i][new_pos.j]) {

                        if (cell(new_pos).owner != me()) {
                            trobat = true;
                            sol = new_pos;
                        }
                    mapa.insert({new_pos, {act, direc}});
                    Cola.push(new_pos);
                    enc[new_pos.i][new_pos.j] = true;
                }
                if (trobat) {
                    break;
                }
            }
            if (trobat) {
                break;
            }
        }
        if (trobat) {
            int dist = 1;
            Pos dir = sol;
            while (dir != pos) {
                auto it = mapa.find(dir);
                if ((it->second).first == pos) {
                    Dir hacer = (it->second).second;
                    movimientos.push({dist, {id, hacer}});
                }
                ++dist;
                dir = (it->second).first;
            }
        }
    }
    // funcion que te devuelve la Dir del zombie
    int dime_donde_esta(Pos pos) {
        for (int i = 0; i < 4; ++i) {
            Pos otro_pos = pos + dire_zomb[i];
            if (pos_ok(otro_pos)) {
                if (cell(otro_pos).id >= 0 and unit(cell(otro_pos).id).type == Zombie) return i;
            }
        }
        return -1;
    }
    // funcion que hace movimientos segun el zombie
    void escapar(priority_queue<colita, vector<colita>, greater<colita>>& movimientos, int id, Pos pos, int n, int m, pair<Dir, pair<int, pair<Pos, Pos>>>& zom) {
        int dist = zom.second.first;
        if (dist == 1) movimientos.push({dist, {id, zom.first}});
        else if (dist == 2) {
            if (not hay_zombie(pos)) movimientos.push({dist, {id, DR}});
            else {
                int zombi = dime_donde_esta(pos);
                if (pos_ok(pos + dire[(zombi + 2)%4])) movimientos.push({dist, {id, dire[(zombi + 2)%4]}});
                else if (pos_ok(pos + dire[(zombi + 3)%4]))  movimientos.push({dist, {id, dire[(zombi + 3)%4]}});
                else if (pos_ok(pos + dire[(zombi + 1)%4]))  movimientos.push({dist, {id, dire[(zombi + 1)%4]}});
                else if (pos_ok(pos + dire[(zombi)%4]))  movimientos.push({dist, {id, dire[(zombi)%4]}});
            }
        }
        else {
            if (not hay_zombie(pos + zom.first)) movimientos.push({dist, {id, zom.first}});
            else {
                int zombi = dime_donde_esta(pos + zom.first);
                if (pos_ok(pos + dire[(zombi + 2)%4])) movimientos.push({dist, {id, dire[(zombi + 2)%4]}});
                else if (pos_ok(pos + dire[(zombi + 3)%4]))  movimientos.push({dist, {id, dire[(zombi + 3)%4]}});
                else if (pos_ok(pos + dire[(zombi + 1)%4]))  movimientos.push({dist, {id, dire[(zombi + 1)%4]}});
                else if (pos_ok(pos + dire[(zombi)%4]))  movimientos.push({dist, {id, dire[(zombi)%4]}});
            }
        }
    }
    // funcion para ir a lo mas cercano
    void suicidarse(int id, Pos pos, int n, int m, priority_queue<colita, vector<colita>, greater<colita>>& movimientos) {
        queue<Pos> Cola;
        Cola.push(pos);
        vector<vector<bool>> enc (n, vector<bool>(m, false));
        map<Pos, pair<Pos, Dir>> mapa;
        bool trobat = false;
        Pos sol;
        enc[pos.i][pos.j] = true;

        while (not Cola.empty()) {
            Pos act = Cola.front();
            Cola.pop();
            for (int i = 0; i < 4; ++i) {
                Dir direc = dire[i];
                Pos new_pos = act + direc;
                if (pos_ok(new_pos) and cell(new_pos).type != Waste and
                    not enc[new_pos.i][new_pos.j]) {
                        int dni = cell(new_pos.i, new_pos.j).id;
                        
                        if (dni >= 0 and unit(dni).type == Zombie) {
                            trobat = true;
                            sol = new_pos;
                        }
                        else if (dni >= 0 and unit(dni).type == Alive and unit(dni).player != me()) {
                            trobat = true;
                            sol = new_pos;
                        }
                        else if (cell(new_pos).food) {
                            trobat = true;
                            sol = new_pos;
                        }
                    mapa.insert({new_pos, {act, direc}});
                    Cola.push(new_pos);
                    enc[new_pos.i][new_pos.j] = true;
                }
                if (trobat) {
                    break;
                }
            }
            if (trobat) {
                break;
            }
        }
        if (trobat) {
            int dist = 1;
            Pos dir = sol;
            while (dir != pos) {
                auto it = mapa.find(dir);
                if ((it->second).first == pos) {
                    Dir hacer = (it->second).second;
                    movimientos.push({dist, {id, hacer}});
                }
                ++dist;
                dir = (it->second).first;
            }
        }
    }
    
    /**
     * Play method, invoked once per each round.
     */
    virtual void play() {
        // evitar out of time
        double tiempo = status(me());
        if (tiempo >= 0.95) return;

        // vector de mis unidades
        vector<int> J = alive_units(me());

        // cola de prioridades de movimientos
        priority_queue<colita, vector<colita>, greater<colita>> movimientos; //numero a ordenar(distancia), id y direccion

        int n = board_rows();
        int m = board_cols();
        int total = num_ini_food();

        // vector de la comida
        vector<Pos> v_comida = pos_de_comida(n, m, total);
        // mapa de id, distancia y direccion para cada comida-id
        map<int, pair<int,Dir>> comida; 

        for (int i = 0; i < total; ++i) asignar_comida(v_comida[i], n, m, comida);

        //escapar zombie
        /*si 3 recto ir a por el
        si 3  diagonal ir direccion contraria
        si 2 diagonal direccion contraria
        si 2 recto quieto
        si 1 comer*/

        for (int id : J) {
            if (unit(id).rounds_for_zombie == -1) {
                pair<Dir, pair<int, pair<Pos, Pos>>> zom = matar_zombie(id, unit(id).pos, n, m);
                pair<Dir, pair<int, pair<Pos, Pos>>> gen = matar_gente(id, unit(id).pos, n, m);
                pair<int, Dir> movimiento_comida;
                auto it = comida.find(id);
                if (it != comida.end()) movimiento_comida = it->second;
                else movimiento_comida = {-1, DR};

                // caso comida
                if (movimiento_comida.first != -1 and zom.second.first > 3 and (movimiento_comida.first <= gen.second.first or gen.second.first > 1)) { 

                    movimientos.push({(movimiento_comida.first)*3, {id, movimiento_comida.second}});

                }
                // caso zombie
                else if (zom.second.first <= 3 and zom.second.first < gen.second.first) {

                    escapar(movimientos, id, unit(id).pos, n, m, zom);

                }
                //si gente es lo más cerca
                else {
                    if (gen.second.first == 1) movimientos.push({0, {id, gen.first}});
                    else if (gen.second.first < 3) movimientos.push({(gen.second.first)*1000, {id, gen.first}});
                    else conquistar_terrritorio(movimientos, id, unit(id).pos, n, m);
                } 
            }
            else suicidarse(id, unit(id).pos, n, m, movimientos);
        }

        while (not movimientos.empty()) {
            colita topo = movimientos.top();
            movimientos.pop();
            move(topo.second.first, topo.second.second);
        }
    }
};

/**
 * Do not modify the following line.
 */
RegisterPlayer(PLAYER_NAME);
