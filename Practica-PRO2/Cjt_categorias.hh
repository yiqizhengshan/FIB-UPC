/** @file Cjt_categorias.hh
    @brief Especificación de la clase Cjt_categorias
*/

#ifndef _CJT_CATEGORIAS_HH_
#define _CJT_CATEGORIAS_HH_

#ifndef NO_DIAGRAM
#include <iostream>
#include <vector>
#endif
using namespace std;

/** @class Cjt_categorias
    @brief Representa un conjunto de categorias
*/
class Cjt_categorias
{
private:
    /** @brief número máximo de categorías*/
    int C;

    /** @brief nivel máximo de cada categoría*/
    int K;

    /** @brief Matriz que representa los puntos que se consiguen según la categoría del Torneo y nivel del jugador*/
    vector<vector<int>> matriz_puntos;
    
    /** @brief Vector que guarda el nombre de cada categoría*/
    vector<string> nombre_cat;

public:
    // Constructoras

    /** @brief Creadora por defecto.
    
        Se ejecuta automáticamente al declarar un conjunto de jugadores.
        \pre <em>cierto</em>
        \post el resultado es una matriz de puntos vacia
        y un vector de nombres vacio
    */
    Cjt_categorias();

    // Consultoras

    /** @brief Consulta la validez de una categoría.
    
        \pre <em>cierto</em>
        \post devuelve <em>true</em> si la categoría está entre 1 y c, <em>false</em> si no
    */
    bool valido(int c) const;

    /** @brief Listar categorias.
    
        \pre <em>cierto</em>
        \post se listan, por orden creciente de identificador, el nombre y la tabla
        de puntos por niveles (en orden creciente de nivel) de cada categoría del circuito
    */
    void listar_categorias() const;

    /** @brief Consulta del nombre de una categoria
    
        \pre n >= 1, n representa la categoría
        \post el resultado es el nombre de la categoría n
    */
    string consultar_nombre(int n) const;

    /** @brief Consulta los puntos de una categoria y nivel
    
        \pre n >= 1, n representa la categoría
        \post el resultado es el entero de matriz_puntos[n][m]
    */
    int consultar_punto(int n, int m) const;

    // Modificadores

    // Lectura/Escritura

    /** @brief Lectura de los nombres de las categorias.
    
        \pre C >= 1, C representa el número de string que leeremos
        \post el parámetro implícito contiene el conjunto de nombres de cada categoría
    */
    void leer_nombres(int C);

    /** @brief Lectura de los puntos por categoría y nivel.
    
        \pre C >= 1, K >= 4
        \post el parámetro implícito contiene los puntos por categoría y nivel
    */
    void leer_puntos(int C, int K);

};
#endif