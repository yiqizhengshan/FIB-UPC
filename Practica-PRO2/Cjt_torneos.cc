/** @file Cjt_torneos.cc
    @brief Código de la clase Cjt_torneos
*/

#include "Cjt_torneos.hh"

// Constructoras

Cjt_torneos::Cjt_torneos() {

}

// Consultoras

   
bool Cjt_torneos::existe(const string &t) const {
    map<string, Torneo>::const_iterator it = Torneos.find(t);
    if (it == Torneos.end()) return false;
    return true;
}

// Modificadores
   
bool Cjt_torneos::afegir_torneo(int c, const string &t) {
    map<string, Torneo>::iterator it = Torneos.find(t);
    if (it == Torneos.end()) {
        Torneo tor(c);
        Torneos.insert(make_pair(t, tor));
        return true;
    }
    return false;
}

bool Cjt_torneos::baja_torneo(const string &t , Cjt_jugadores &jugadores) {
    map<string, Torneo>::iterator it = Torneos.find(t);
    if (it != Torneos.end()) {
        if (not it->second.consultar_primera_edicion()) {
            it->second.quitar_puntos(jugadores);
            jugadores.actualizar_ranking();
        }
        Torneos.erase(it);
        return true;
    }
    return false;
}

void Cjt_torneos::baja_jugador_torneo(const string &p) {
    map<string, Torneo>::iterator it = Torneos.begin();
    while (it != Torneos.end()) {
        if (not it->second.consultar_primera_edicion()) {
            it->second.eliminar_jugador(p);
        }
        ++it;
    }
}
   
void Cjt_torneos::inicializar_torneo(const string &t, const Cjt_jugadores& jugadores) {
    int n;
    cin >> n;
    map<string, Torneo>::iterator it = Torneos.find(t);
    it->second.leer_jugadores_inscritos(n, jugadores);
    int altura_max = ceil(log2(n) + 1);
    it->second.confeccionar_cuadro_emparejamiento(n, altura_max);
    it->second.escribir_cuadro_emparejamiento();

}

   
void Cjt_torneos::finalizar_torneo(const string &t, const Cjt_categorias& categoria, Cjt_jugadores& jugadores) {
    map<string, Torneo>::iterator it = Torneos.find(t);
    vector<Partido> resultados;
    it->second.confeccionar_cuadro_resultados(jugadores, resultados);
    it->second.escribir_cuadro_resultados(categoria, jugadores, resultados);
    it->second.quitar_puntos(jugadores);
    it->second.sumar_puntos(jugadores);
    jugadores.actualizar_ranking();
    it->second.actualizar_jugadores_anteriores();
    if (it->second.consultar_primera_edicion()) {
        it->second.actualizar_edicion();
    }
}

// Lectura/Escritura
    
void Cjt_torneos::leer_torneos(int T) {
    for (int i = 0; i < T; ++i) {
        string s;
        int n;
        cin >> s >> n;
        Torneo t(n);
        Torneos.insert(make_pair(s, t));
    }
}

void Cjt_torneos::listar_torneos(const Cjt_categorias &categoria) const{
    map<string, Torneo>::const_iterator it = Torneos.begin();
    while (it != Torneos.end()) {
        cout << it->first << ' ' << categoria.consultar_nombre(it->second.consultar_categoria()) << endl;
        ++it;
    }
}