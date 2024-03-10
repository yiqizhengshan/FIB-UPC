/** @file Torneo.cc
    @brief Código de la clase Torneo
*/

#include "Torneo.hh"

//Constructoras

Torneo::Torneo() {
    primera_edicion = true;
}

Torneo::Torneo(int cat) {
    categoria = cat;
    primera_edicion = true;
}

// Consultoras

int Torneo::consultar_categoria() const {
    return categoria;
}

bool Torneo::consultar_primera_edicion() const {
    return primera_edicion;
}

//Modificadoras

void Torneo::actualizar_jugadores_anteriores() {
    cjt_jugadores_anteriores = cjt_jugadores_inscritos;
}

void Torneo::actualizar_edicion() {
    primera_edicion = false;
}

void Torneo::quitar_puntos(Cjt_jugadores &jugadores) {
    int n = cjt_jugadores_anteriores.size();
    for (int i = 0; i < n; ++i) {
        jugadores.restar(cjt_jugadores_anteriores[i].nombre, cjt_jugadores_anteriores[i].puntos);
    }
}

void Torneo::sumar_puntos(Cjt_jugadores &jugadores) {
    int n = cjt_jugadores_inscritos.size();
    for (int i = 0; i < n; ++i) {
        
        jugadores.sumar(cjt_jugadores_inscritos[i].nombre, cjt_jugadores_inscritos[i].puntos);
    }
}

void Torneo::eliminar_jugador(const string &p) {
    int n = cjt_jugadores_anteriores.size();
    for (int i = 0; i < n; ++i) {
        if (cjt_jugadores_anteriores[i].nombre == p) {
            cjt_jugadores_anteriores[i].puntos = 0;
        }
    }
}

// Lectura/Escritura

void Torneo::confeccionar_cuadro_emparejamiento(int n, int altura_max) {
    num_jugadores = n;
    cuadro_emparejamiento = i_confeccionar_cuadro_emparejamiento(1, n, 1, altura_max, 2);
}

void Torneo::confeccionar_cuadro_resultados(Cjt_jugadores& jugadores, vector<Partido> &resultados) {
    int n = (num_jugadores*2) - 1;
    resultados = vector<Partido> (n);
    for (int i = 0; i < n; ++i) {
        resultados[i].leer();
    }
    int j = 0;
    i_confeccionar_cuadro_resultados(cuadro_emparejamiento, j, resultados, jugadores, cjt_jugadores_inscritos);
}

void Torneo::escribir_cuadro_emparejamiento() const{
    write(cuadro_emparejamiento, cjt_jugadores_inscritos);
    cout << endl;
}

void Torneo::escribir_cuadro_resultados(const Cjt_categorias &categorias, Cjt_jugadores &jugadores, vector<Partido> &resultados) {
    int j = 0;
    write_2(cuadro_emparejamiento, cjt_jugadores_inscritos, resultados, categorias, j, 0, categoria);
    cout << endl;
    for (int i = 0; i < cjt_jugadores_inscritos.size(); ++i) {
        string nom = cjt_jugadores_inscritos[i].nombre;
        if (cjt_jugadores_inscritos[i].puntos > 0) {
            cout << i + 1 << '.' << nom << ' ' << cjt_jugadores_inscritos[i].puntos << endl;
        }
        jugadores.sumar_torneo(nom);
    }
}

void Torneo::leer_jugadores_inscritos(int n, const Cjt_jugadores &jugadores) {
    cjt_jugadores_inscritos = vector<Inscrito> (n);
    for (int i = 0; i < n; ++i) {
        int num;
        cin >> num;
        cjt_jugadores_inscritos[i].nombre = jugadores.consultar_nombre(num);
        cjt_jugadores_inscritos[i].dado_puntos = false;
        cjt_jugadores_inscritos[i].puntos = 0;
    }
}

// Static

BinTree<int> Torneo::i_confeccionar_cuadro_emparejamiento(int v, int n, int altura, int altura_max, int pot) {
    if (altura == altura_max - 1) {
        if(v <= n and v > pot - n){
            int b = pot + 1 - v;
            BinTree<int> e(v);
            BinTree<int> d(b);
            return BinTree<int> (v,e,d);
        }
        else return BinTree<int>(v);
    }
    else if (altura != altura_max){
        BinTree<int> ae;
        BinTree<int> ad;
        int b = pot + 1 - v;
        ae = i_confeccionar_cuadro_emparejamiento(v, n, altura + 1, altura_max, pot*2);
        ad = i_confeccionar_cuadro_emparejamiento(b, n, altura + 1, altura_max, pot*2);
        return BinTree<int> (v, ae, ad);
    }
    return BinTree<int>();
}

void Torneo::write(const BinTree<int> &a, const vector<Inscrito> &v){
    if (not a.empty()) {
        if(a.left().empty()) {
            int n = a.value();
            cout << n << '.';
            cout << v[n - 1].nombre;
        }
        else {
            cout << '(';
            write(a.left(), v);
            cout << ' ';
            write(a.right(), v);
            cout << ')';
        }
    }
}

void Torneo::i_confeccionar_cuadro_resultados(BinTree<int> &cuadro, int &numero, vector<Partido> &resultados, Cjt_jugadores &jugadores, const vector<Inscrito> &inscritos) {
    string s = resultados[numero].consultar_string();
    if (s != "0") {
        int numero_act = numero;
        bool ganador = resultados[numero].calcular_stats(); // devuelve false si el ganador es el izquierda

        ++numero;
        BinTree<int> ae = cuadro.left();
        BinTree<int> ad = cuadro.right();
        i_confeccionar_cuadro_resultados(ae, numero, resultados, jugadores, inscritos);
        i_confeccionar_cuadro_resultados(ad, numero, resultados, jugadores, inscritos);
        if (not ganador) {
            BinTree<int> arbre(ae.value(), ae, ad);
            cuadro = arbre;
        }
        else {
            BinTree<int> arbre(ad.value(), ae, ad);
            cuadro = arbre;
        }
        jugadores.actualizar_estadisticas(resultados[numero_act], inscritos[cuadro.left().value() - 1].nombre, inscritos[cuadro.right().value() - 1].nombre);

    }
    else {
        ++numero;
    }
}

void Torneo::write_2(const BinTree<int> &a, vector<Inscrito> &v, const vector<Partido> &resultados, const Cjt_categorias &categorias, int &numero, int altura, int categoria){
    string s = resultados[numero].consultar_string();

    if (s != "0") {
        cout << '(';
        int n = a.left().value();
        int m = a.right().value();

        if (not resultados[numero].consultar_winner()) {
            if (not v[n - 1].dado_puntos) {
                v[n - 1].puntos = categorias.consultar_punto(categoria - 1, altura);
                v[n - 1].dado_puntos = true;
            }
            if (not v[m - 1].dado_puntos) {
                v[m - 1].puntos = categorias.consultar_punto(categoria - 1, altura + 1);
                v[m - 1].dado_puntos = true;

            }
        }
        else {
            if (not v[n - 1].dado_puntos) {
                v[n - 1].puntos = categorias.consultar_punto(categoria - 1, altura + 1);
                v[n - 1].dado_puntos = true;
            }
            if (not v[m - 1].dado_puntos) {
                v[m - 1].puntos = categorias.consultar_punto(categoria - 1, altura);
                v[m - 1].dado_puntos = true;
            }
        }
        ++numero;
        cout << n << '.' << v[n - 1].nombre << " vs " << m << '.' << v[m - 1].nombre << ' ' << s;
        write_2(a.left(), v, resultados, categorias, numero, altura + 1, categoria);
        write_2(a.right(), v, resultados, categorias, numero, altura + 1, categoria);
        cout << ')';
    }
    else {
        ++numero;
    }
}