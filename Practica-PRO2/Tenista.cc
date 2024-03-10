/** @file Tenista.cc
    @brief Código de la clase Tenista
*/

#include "Tenista.hh"

// Constructoras

Tenista::Tenista() {
    puntos = 0;
    posicion = 0;
    torneos_jugados = 0;
    juegos_ganados = 0;
    juegos_perdidos = 0;
    sets_ganados = 0;
    sets_perdidos = 0;
    partidos_ganados = 0;
    partidos_perdidos = 0;
}
 
Tenista::Tenista(int p) {
    puntos = 0;
    posicion = p;
    torneos_jugados = 0;
    juegos_ganados = 0;
    juegos_perdidos = 0;
    sets_ganados = 0;
    sets_perdidos = 0;
    partidos_ganados = 0;
    partidos_perdidos = 0;
}

// Consultoras

int Tenista::consultar_puntos() const {
    return puntos;
}

int Tenista::consultar_posicion() const {
    return posicion;
}

int Tenista::consultar_torneos_jugados() const {
    return torneos_jugados;
}

int Tenista::consultar_juegos_ganados() const {
    return juegos_ganados;
}

int Tenista::consultar_juegos_perdidos() const {
    return juegos_perdidos;
}

int Tenista::consultar_sets_ganados() const {
    return sets_ganados;
}

int Tenista::consultar_sets_perdidos() const {
    return sets_perdidos;
}

int Tenista::consultar_partidos_ganados() const {
    return partidos_ganados;
}

int Tenista::consultar_partidos_perdidos() const {
    return partidos_perdidos;
}

// Modificadores

void Tenista::sumar_puntos(int n) {
    puntos += n;
}

void Tenista::restar_puntos(int n) {
    puntos -= n;
}

void Tenista::modif_posicion(int n) {
    posicion = n;
}

void Tenista::modif_torneos_jugados() {
    ++torneos_jugados;
}

void Tenista::modif_juegos_ganados(int n) {
    juegos_ganados = n;
}

void Tenista::modif_juegos_perdidos(int n) {
    juegos_perdidos = n;
}

void Tenista::modif_sets_ganados(int n) {
    sets_ganados = n;
}

void Tenista::modif_sets_perdidos(int n) {
    sets_perdidos = n;
}

void Tenista::modif_partidos_ganados(int n) {
    partidos_ganados = n;
}
    
void Tenista::modif_partidos_perdidos(int n) {
    partidos_perdidos = n;
}

void Tenista::actualizar_stats(const Partido &p, bool ganador, bool dre) {
    string s = p.consultar_string();
    if (not dre) {
        int juegos_ganados_izq = p.consultar_juegos_ganados_izq();
        int juegos_perdidos_izq = p.consultar_juegos_perdidos_izq();
        int sets_ganados_izq = p.consultar_sets_ganados_izq();
        int sets_perdidos_izq = p.consultar_sets_perdidos_izq();
        if (ganador) {
            if (s == "1-0" or s == "0-1") ++partidos_ganados;
            else {
                ++partidos_ganados;
                juegos_ganados += juegos_ganados_izq;
                juegos_perdidos += juegos_perdidos_izq;
                sets_ganados += sets_ganados_izq;
                sets_perdidos += sets_perdidos_izq;
            }
        }
        else {
            if (s == "1-0" or s == "0-1") ++partidos_perdidos;
            else {
                ++partidos_perdidos;
                juegos_ganados += juegos_ganados_izq;
                juegos_perdidos += juegos_perdidos_izq;
                sets_ganados += sets_ganados_izq;
                sets_perdidos += sets_perdidos_izq;
            }
        }
    }
    else {
        int juegos_ganados_der = p.consultar_juegos_ganados_der();
        int juegos_perdidos_der = p.consultar_juegos_perdidos_der();
        int sets_ganados_der = p.consultar_sets_ganados_der();
        int sets_perdidos_der = p.consultar_sets_perdidos_der();
        if (ganador) {
            if (s == "1-0" or s == "0-1") ++partidos_ganados;
            else {
                ++partidos_ganados;
                juegos_ganados += juegos_ganados_der;
                juegos_perdidos += juegos_perdidos_der;
                sets_ganados += sets_ganados_der;
                sets_perdidos += sets_perdidos_der;
            }
        }
        else {
            if (s == "1-0" or s == "0-1") ++partidos_perdidos;
            else {
                ++partidos_perdidos;
                juegos_ganados += juegos_ganados_der;
                juegos_perdidos += juegos_perdidos_der;
                sets_ganados += sets_ganados_der;
                sets_perdidos += sets_perdidos_der;
            }
        }
    }
}

// Lectura/Escritura

void Tenista::escribir_tenista() const{
    cout << " Rk:" << posicion << " Ps:" << puntos << " Ts:"
    << torneos_jugados << " WM:" << partidos_ganados << " LM:"
    << partidos_perdidos << " WS:" << sets_ganados << " LS:"
    << sets_perdidos << " WG:" << juegos_ganados << " LG:"
    << juegos_perdidos << endl;
}
