/** @file Cjt_categorias.cc
    @brief Código de la clase Cjt_categorias
*/

#include "Cjt_categorias.hh"

// Constructoras

Cjt_categorias::Cjt_categorias() {

}

// Consultoras

bool Cjt_categorias::valido(int c) const {
    if (c > 0 and c <= C) return true;
    
    return false;
}

void Cjt_categorias::listar_categorias() const {
    cout << C << ' ' << K << endl;
    for (int i = 0; i < C; ++i) {
        cout << nombre_cat[i];
        for (int j = 0; j < K; ++j) {
            cout << ' ' << matriz_puntos[i][j];
        }
        cout << endl;
    }
}

string Cjt_categorias::consultar_nombre(int n) const{
    return nombre_cat[n - 1];
}

int Cjt_categorias::consultar_punto(int n, int m) const{
    return matriz_puntos[n][m];
}

// Modificadores

// Lectura/Escritura

void Cjt_categorias::leer_nombres(int C) {
    this->C = C;
    nombre_cat = vector<string> (C);
    for (int i = 0; i < C; ++i) {
        cin >> nombre_cat[i];
    }
}

void Cjt_categorias::leer_puntos(int C, int K) {
    this->K = K;
    matriz_puntos = vector<vector<int>> (C, vector<int> (K));
    for (int i = 0; i < C; ++i) {
        for (int j = 0; j < K; ++j) {
            cin >> matriz_puntos[i][j];
        }
    }
}