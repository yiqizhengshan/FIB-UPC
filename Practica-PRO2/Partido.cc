/** @file Partido.cc
    @brief Código de la clase Partido
*/

#include "Partido.hh"

//Constructoras

Partido::Partido() {
    juegos_ganados_izq = 0;
    juegos_perdidos_izq = 0;
    sets_ganados_izq = 0;
    sets_perdidos_izq = 0;

    juegos_ganados_der = 0;
    juegos_perdidos_der = 0;
    sets_ganados_der = 0;
    sets_perdidos_der = 0;
}

// Consultoras

string Partido::consultar_string() const{
    return resultado;
}

bool Partido::consultar_winner() const{
    return winner;
}

int Partido::consultar_juegos_ganados_izq() const {
    return juegos_ganados_izq;
}
int Partido::consultar_juegos_perdidos_izq() const {
    return juegos_perdidos_izq;
}
int Partido::consultar_sets_ganados_izq() const {
    return sets_ganados_izq;
}
int Partido::consultar_sets_perdidos_izq() const {
    return sets_perdidos_izq;
}

int Partido::consultar_juegos_ganados_der() const {
    return juegos_ganados_der;
}
int Partido::consultar_juegos_perdidos_der() const {
    return juegos_perdidos_der;
}
int Partido::consultar_sets_ganados_der() const {
    return sets_ganados_der;
}
int Partido::consultar_sets_perdidos_der() const {
    return sets_perdidos_der;
}

// Modificadoras

bool Partido::calcular_stats() {
    int n = resultado.length();
    if (n == 11) {
        int a, b, c, d, e, f;
        a = resultado[0] - '0';
        b = resultado[2] - '0';
        c = resultado[4] - '0';
        d = resultado[6] - '0';
        e = resultado[8] - '0';
        f = resultado[10] - '0';
        int res_esq = a + c + e;
        int res_dre = b + d + f;
        juegos_ganados_izq += res_esq;
        juegos_perdidos_izq += res_dre;
        juegos_ganados_der += res_dre;
        juegos_perdidos_der += res_esq;
        if (a > b) {
            ++sets_ganados_izq;
            ++sets_perdidos_der;
        }
        else {
            ++sets_ganados_der;
            ++sets_perdidos_izq;
        }

        if (c > d) {
            ++sets_ganados_izq;
            ++sets_perdidos_der;
        }
        else {
            ++sets_ganados_der;
            ++sets_perdidos_izq;
        }

        if (e > f) {
            ++sets_ganados_izq;
            ++sets_perdidos_der;
        }
        else {
            ++sets_ganados_der;
            ++sets_perdidos_izq;
        }

        winner = sets_ganados_izq < sets_ganados_der;

    }
    else if (n == 7) {
        int a, b, c, d;
        a = resultado[0] - '0';
        b = resultado[2] - '0';
        c = resultado[4] - '0';
        d = resultado[6] - '0';
        int res_esq = a + c;
        int res_dre = b + d;
        juegos_ganados_izq += res_esq;
        juegos_perdidos_izq += res_dre;
        juegos_ganados_der += res_dre;
        juegos_perdidos_der += res_esq;
        if (a > b) {
            ++sets_ganados_izq;
            ++sets_perdidos_der;
        }
        else {
            ++sets_ganados_der;
            ++sets_perdidos_izq;
        }

        if (c > d) {
            ++sets_ganados_izq;
            ++sets_perdidos_der;
        }
        else {
            ++sets_ganados_der;
            ++sets_perdidos_izq;
        }

        winner = sets_ganados_izq < sets_ganados_der;

    }
    else {
        if (resultado[0] > resultado[2]) winner = false;
        else winner = true;
    }


    return winner;
}

// Lectura/Escritura

void Partido::leer() {
    cin >> resultado;
}