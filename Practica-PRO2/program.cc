/**
    @mainpage
    Circuito de torneos de tenis.
    
    Práctica resuelta, con documentación completa (incluyendo elementos privados y código).

    El programa principal se encuentra en el módulo program.cc. Atendiendo a los tipos de datos sugeridos en el enunciado, necesitaremos un módulo para representar el Tenista, otro para el Torneo y otro para el Partido.
*/

/** @file program.cc
    @brief Programa principal para el ejercicio <em>Circuito de torneos de tenis</em>.
*/

#include "Cjt_torneos.hh"
#include "Cjt_categorias.hh"
#include "Cjt_jugadores.hh"
#ifndef NO_DIAGRAM
#include <iostream>
#endif
using namespace std;

/** @brief Programa principal para el ejercicio <em>Circuito de torneos de tenis</em>.
*/
int main() {
    int C, K;
    cin >> C >> K;

    Cjt_categorias categoria;
    categoria.leer_nombres(C);
    categoria.leer_puntos(C, K);

    int T;
    cin >> T;
    Cjt_torneos torneos;
    torneos.leer_torneos(T);
    
    int P;
    cin >> P;
    Cjt_jugadores jugadores;
    jugadores.leer_jugadores(P);

    string comando;
    cin >> comando;
    while (comando != "fin") {
        cout << '#' << comando;
        if (comando == "nuevo_jugador" or comando == "nj") {
            string p;
            cin >> p;
            cout << ' ' << p << endl;
            if (jugadores.afegir_jugador(p)) {
                ++P;
                cout << P << endl;
            }
            else {
                cout << "error: ya existe un jugador con ese nombre" << endl;
            }
        }
        else if (comando == "nuevo_torneo" or comando == "nt") {
            string t;
            int c;
            cin >> t >> c;
            cout << ' ' << t << ' ' << c << endl;
            if (not categoria.valido(c)) {
                cout << "error: la categoria no existe" << endl;
            }
            else if (torneos.afegir_torneo(c, t)) {
                ++T;
                cout << T << endl;
            }
            else {
                cout << "error: ya existe un torneo con ese nombre" << endl;
            }

        }
        else if (comando == "baja_jugador" or comando == "bj") {
            string p;
            cin >> p;
            cout << ' ' << p << endl;
            if (jugadores.baja_jugador(p)) {
                torneos.baja_jugador_torneo(p);
                --P;
                cout << P << endl;
            }
            else {
                cout << "error: el jugador no existe" << endl;
            }
        }
        else if (comando == "baja_torneo" or comando == "bt") {
            string t;
            cin >> t;
            cout << ' ' << t << endl;
            if (torneos.baja_torneo(t, jugadores)) {
                --T;
                cout << T << endl;
            }
            else cout << "error: el torneo no existe" << endl;
        }
        else if (comando == "iniciar_torneo" or comando == "it") {
            string t;
            cin >> t;
            cout << ' ' << t << endl;
            torneos.inicializar_torneo(t, jugadores);
        }
        else if (comando == "finalizar_torneo" or comando == "ft") {
            string t;
            cin >> t;
            cout << ' ' << t << endl;
            torneos.finalizar_torneo(t, categoria, jugadores);
        }
        else if (comando == "listar_ranking" or comando == "lr") {
            cout << endl;
            jugadores.listar_ranking();
        }
        else if (comando == "listar_jugadores" or comando == "lj") {
            cout << endl;
            jugadores.listar_jugadores();
        }
        else if (comando == "consultar_jugador" or comando == "cj") {
            string p;
            cin >> p;
            cout << ' ' << p << endl;
                if (not jugadores.consultar_jugador(p)) {
                    cout << "error: el jugador no existe" << endl;
                }
        }
        else if (comando == "listar_torneos" or comando == "lt") {
            cout << endl << T << endl;
            torneos.listar_torneos(categoria);
        }
        else if (comando == "listar_categorias" or comando == "lc") {
            cout << endl;
            categoria.listar_categorias();
        }
        cin >> comando;
    }
}