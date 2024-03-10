/** @file Cjt_jugadores.hh
    @brief Especificación de la clase Cjt_jugadores
*/

#ifndef _CJT_JUGADORES_HH_
#define _CJT_JUGADORES_HH_

#include "Tenista.hh"
#include "Partido.hh"
#ifndef NO_DIAGRAM
#include <map>
#include <vector>
#include <iostream>
#include <algorithm>
#endif
using namespace std;

/** @class Cjt_jugadores
    @brief Representa un conjunto de Tenistas/Participante
*/
class Cjt_jugadores
{
private:
    /** @brief Struct con atributo nombre, puntos que tiene y la posicion que tenía*/
    struct Participante{
        string nombre;
        int puntos;
        int posicion_anterior;
    };

    /** @brief Funcion estática cmp para la función sort*/
    static bool cmp(const Participante &a, const Participante &b);

    /** @brief Mapa de Tenistas ordenado crecientemente por identificador*/
    map<string, Tenista> Jugadores;

    /** @brief Vector de Participantes ordenado por el número de puntos descendientemente*/
    vector<Participante> ranking;

public:
    // Constructoras

    /** @brief Creadora por defecto.
    
        Se ejecuta automáticamente al declarar un conjunto de jugadores.
        \pre <em>cierto</em>
        \post el resultado es un conjunto de jugadores y un ranking vacio
    */
    Cjt_jugadores();

    // Consultoras

    /** @brief Consultar nombre de una posicion.
    
        \pre <em>cierto</em>
        \post el resultado devuelve el nombre del jugador en posicion pos del ranking
    */
    string consultar_nombre(int pos) const;

    // Modificadores

    /** @brief Añadir jugador al conjunto de jugadores y al ranking.
    
        \pre <em>cierto</em>
        \post Si existe, se le añade un Tenista al parámetro implícito, se actualiza el ranking y devuelve true.
        False al contrario.
    */
    bool afegir_jugador(const string &p);

    /** @brief Dar de baja a un jugador del conjunto de jugadores y del ranking.
    
        \pre <em>cierto</em>
        \post Si existe, se elimina un Tenista al parámetro implícito, se actualiza el ranking y devuelve true.
        False al contrario.
    */
    bool baja_jugador(const string &p);

    /** @brief Actualizar ranking.
    
        \pre <em>cierto</em>
        \post realiza un sort del ranking
    */
    void actualizar_ranking();

    /** @brief Sumar puntos al jugador.
    
        \pre <em>cierto</em>
        \post se le suman <em>punts</em> puntos al Participante/Tenista p
    */
    void sumar(const string &p, int punts);

    /** @brief Restar puntos al jugador.
    
        \pre <em>cierto</em>
        \post se le restan <em>puntos</em> puntos al Participante/Tenista p
    */
    void restar(const string &p, int punts);

    /** @brief Actualizar estadisticas.
    
        \pre tanto el jugador con identificador <em>a</em> como <em>b</em> existen en el conjunto de jugadores 
        \post actualiza las estadisticas de los jugadores dado un partido
    */
    void actualizar_estadisticas(const Partido &p, const string &a, const string &b);

    /** @brief Sumar torneo disputado.
    
        \pre el jugador con identificador <em>s</em> existe dentro del conjunto de jugadores
        \post le suma un torneo disputado al jugador s
    */
    void sumar_torneo(const string &s);

    // Lectura/Escritura

    /** @brief Lectura de los jugadores iniciales.
    
        \pre P >= 0, P representa el número de string que leeremos
        \post el parámetro implícito contiene el conjunto de jugadores inicial
        y, el ranking inicial segun el orden de lectura de cada jugador
    */
    void leer_jugadores(int P);

    /** @brief Escribir nombre del jugador en la posición pos.
    
        \pre 0 <= pos <= ranking.size()
        \post escribe el nombre del jugador en la posicion <em>pos</em>
    */
    void escribir_nombre(int pos) const;

    /** @brief Listar ranking.
    
        \pre <em>cierto</em>
        \post se listan, por orden creciente de ranking actual, la posición,
        el nombre y los puntos de cada jugador del conjunto de jugadores
    */
    void listar_ranking() const;

    /** @brief Listar jugadores.
    
        \pre <em>cierto</em>
        \post se listan, por orden creciente de identificador, el nombre, la
        posición en el ranking, los puntos y el resto de las estadísticas de
        cada jugador del conjunto de jugadores
    */
    void listar_jugadores() const;

    /** @brief Consultar jugador.
    
        \pre <em>cierto</em>
        \post Si existe el Tenista, se imprime el nombre, la posición en el ranking, los puntos y
        el resto de las estadísticas del jugador p y devuelve true. Sino solo devuelve false.
    */
    bool consultar_jugador(const string &p) const;

};
#endif