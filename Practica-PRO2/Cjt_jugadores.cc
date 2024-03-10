/** @file Cjt_jugadores.cc
    @brief Código de la clase Cjt_jugadores
*/

#include "Cjt_jugadores.hh"

// Constructoras

Cjt_jugadores::Cjt_jugadores() {

}

// Consultoras
string Cjt_jugadores::consultar_nombre(int pos) const {
    return ranking[pos - 1].nombre;
}

// Modificadores

bool Cjt_jugadores::afegir_jugador(const string &p) {
    map<string, Tenista>::iterator it = Jugadores.find(p);
    if (it == Jugadores.end()) {
        Participante part;
        part.nombre = p;
        part.puntos = 0;
        int pos = ranking.size() + 1;
        part.posicion_anterior = pos;
        ranking.push_back(part);

        Tenista t(pos);
        Jugadores.insert(make_pair(p, t));
        return true;
    }
    return false;

}

bool Cjt_jugadores::baja_jugador(const string &p) {
    map<string, Tenista>::iterator it = Jugadores.find(p);
    if (it != Jugadores.end()) {
        int pos = it->second.consultar_posicion() - 1;
        it = Jugadores.erase(it);
    
        int n = ranking.size() - 1;

        for (int i = pos; i < n; ++i) {
            ranking[i] = ranking[i + 1];
            ranking[i].posicion_anterior = i + 1;
            map<string, Tenista>::iterator it = Jugadores.find(ranking[i].nombre);
            it->second.modif_posicion(i + 1);
        }
        ranking.pop_back();
        return true;
    }
    return false;
}

void Cjt_jugadores::actualizar_ranking() {
    sort(ranking.begin(), ranking.end(), cmp);
    for (int i = 0; i < ranking.size(); ++i) {
        ranking[i].posicion_anterior = i + 1;
        map<string, Tenista>::iterator it = Jugadores.find(ranking[i].nombre);
        it->second.modif_posicion(i + 1);
    }
}

void Cjt_jugadores::sumar(const string &p, int punts) {
    map<string, Tenista>::iterator it = Jugadores.find(p);
    if (it != Jugadores.end()) {
        it->second.sumar_puntos(punts);
        ranking[it->second.consultar_posicion() - 1].puntos += punts;
    }
}

void Cjt_jugadores::restar(const string &p, int punts) {
    map<string, Tenista>::iterator it = Jugadores.find(p);
    if (it != Jugadores.end()) {
        it->second.restar_puntos(punts);
    
       if (ranking[it->second.consultar_posicion() - 1].puntos - punts >= 0)
            ranking[it->second.consultar_posicion() - 1].puntos -= punts;
    }
}

void Cjt_jugadores::actualizar_estadisticas(const Partido &p, const string &a, const string &b) {
    bool ganador = p.consultar_winner();
    map<string, Tenista>::iterator it_esq = Jugadores.find(a);
    map<string, Tenista>::iterator it_dre = Jugadores.find(b);
    if (not ganador) {
        it_esq->second.actualizar_stats(p, true, false);
        it_dre->second.actualizar_stats(p, false, true);
    }
    else {
        it_esq->second.actualizar_stats(p, false, false);
        it_dre->second.actualizar_stats(p, true, true);
    }
}

void Cjt_jugadores::sumar_torneo(const string &s) {
    map<string, Tenista>::iterator it = Jugadores.find(s);
    it->second.modif_torneos_jugados();
}

// Lectura/Escritura

void Cjt_jugadores::leer_jugadores(int P) {
    ranking = vector<Participante>(P);
    for (int i = 0; i < P; ++i) {
        cin >> ranking[i].nombre;
        ranking[i].puntos = 0;
        ranking[i].posicion_anterior = i + 1;

        string s = ranking[i].nombre;
        Tenista t(i + 1);
        Jugadores.insert(make_pair(s, t));
    }
}

void Cjt_jugadores::escribir_nombre(int pos) const{
    cout << ranking[pos - 1].nombre;
}

void Cjt_jugadores::listar_ranking() const{
    int n = ranking.size();
    for (int i = 0; i < n; ++i) {
        cout << ranking[i].posicion_anterior << ' ' << ranking[i].nombre << ' ' << ranking[i].puntos << endl;
    }
}

void Cjt_jugadores::listar_jugadores() const{
    cout << ranking.size() << endl;
    map<string, Tenista>::const_iterator it = Jugadores.begin();
    while (it != Jugadores.end()) {
        cout << it->first;
        it->second.escribir_tenista();
        ++it;
    }
}

bool Cjt_jugadores::consultar_jugador(const string &p) const{
    map<string, Tenista>::const_iterator it = Jugadores.find(p);
    if (it != Jugadores.end()) {
        cout << p;
        it->second.escribir_tenista();
        return true;
    }
    return false;
}

//STATIC

bool Cjt_jugadores::cmp(const Participante &a, const Participante &b) {
        if (a.puntos != b.puntos) return a.puntos > b.puntos;
        return a.posicion_anterior < b.posicion_anterior;
}