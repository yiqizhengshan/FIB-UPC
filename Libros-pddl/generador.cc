#include <iostream>
#include <vector>
#include <random>
#include <fstream>
#include <unordered_set>

using namespace std;

// Función para generar un número aleatorio entre min y max
int generarNumeroAleatorio(int min, int max) {
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dis(min, max);
    return dis(gen);
}

int main() {
    ofstream archivo("output.pddl");

    if (!archivo.is_open()) {
        cerr << "No se pudo abrir el archivo." << endl;
        return 1;
    }

    int numlibros = generarNumeroAleatorio(1, 15);
    unordered_set<int> numeros;

    // Cabecera adicional
    archivo << "(define (problem libros-paralelo)" << endl;
    archivo << "\t(:domain libros)" << endl;

    archivo << "\t(:objects" << endl;
    archivo << "\t\t";
    for (int i = 1; i <= numlibros; ++i) {
        archivo << "l" << i << " ";
    }
    archivo << "- libro" << endl;
    archivo << "\t\tenero febrero marzo abril mayo junio julio agosto septiembre octubre noviembre diciembre - mes" << endl;
    archivo << "\t)" << endl << endl;

    // :init
    archivo << "\t(:init" << endl;

    for (int i = 1; i <= 12; ++i) {
        archivo << "\t\t(= (numero_mes " << (i == 1 ? "enero" : i == 2 ? "febrero" : i == 3 ? "marzo" : i == 4 ? "abril" :
            i == 5 ? "mayo" : i == 6 ? "junio" : i == 7 ? "julio" : i == 8 ? "agosto" : i == 9 ? "septiembre" :
            i == 10 ? "octubre" : i == 11 ? "noviembre" : "diciembre") << ") " << i << ")" << endl;

        archivo << "\t\t(= (paginas_disponibles_mes " << (i == 1 ? "enero" : i == 2 ? "febrero" : i == 3 ? "marzo" : i == 4 ? "abril" :
            i == 5 ? "mayo" : i == 6 ? "junio" : i == 7 ? "julio" : i == 8 ? "agosto" : i == 9 ? "septiembre" :
            i == 10 ? "octubre" : i == 11 ? "noviembre" : "diciembre") << ") 800)" << endl;
    }

    archivo << endl;

    for (int i = 1; i <= numlibros; ++i) {
        archivo << "\t\t(= (mes_leido l" << i << ") 0)" << endl;
    }

    archivo << endl;

    for (int i = 1; i <= numlibros; ++i) {
        archivo << "\t\t(= (paginas l" << i << ") " << generarNumeroAleatorio(1, 800) << ")" << endl;
    }

    archivo << endl;

    for (int i = 1; i <= numlibros; ++i) {
        if (generarNumeroAleatorio(0, 6) == 1) {
            archivo << "\t\t(leido l" << i << ")" << endl;
            numeros.insert(i);
        }
    }

    archivo << endl;

    for (int i = 1; i <= numlibros; ++i) {
        if (generarNumeroAleatorio(0, 1) == 1 and numeros.find(i) == numeros.end()) {
            archivo << "\t\t(quiere_leer l" << i << ")" << endl;
        }
    }

    archivo << endl;

    for (int i = 1; i <= numlibros; ++i) {
        int n_pred = generarNumeroAleatorio(0, 3);
        for (int j = 0; j <= n_pred; ++j) {
             if (generarNumeroAleatorio(0, 3) == 1) {
                int pred = generarNumeroAleatorio(1, numlibros);
                if (i != pred and numeros.find(pred) == numeros.end()) {
                    archivo << "\t\t(es_predecesor l" << i << " l" << pred << ")" << endl;
                }
            }
        }
    }
    
    archivo << endl;

    for (int i = 1; i <= numlibros; ++i) {
        int n_paralelos = generarNumeroAleatorio(0, 3);
        for (int j = 0; j <= n_paralelos; ++j) {
            if (generarNumeroAleatorio(0, 3) == 1 and numeros.find(i) == numeros.end()) {
                int pred = generarNumeroAleatorio(1, numlibros);
                if (i != pred and numeros.find(pred) == numeros.end()) {
                    archivo << "\t\t(es_paralelo l" << i << " l" << pred << ")" << endl;
                    archivo << "\t\t(es_paralelo l" << pred << " l" << i << ")" << endl;
                }
            }
        }
    }

    archivo << "\t)" << endl << endl;   

    // :goal
    archivo << "\t(:goal" << endl;
    archivo << "\t\t(and (forall (?l - libro) (or" << endl;
    archivo << "\t\t\t(and (quiere_leer ?l) (leido ?l))" << endl;
    archivo << "\t\t\t(and (not (quiere_leer ?l)) (not (leido ?l)))" << endl;
    archivo << "\t\t)))" << endl;
    archivo << "\t)" << endl;

    archivo << ")" << endl;

    archivo.close();

    cout << "Archivo generado exitosamente." << endl;

    return 0;
}
