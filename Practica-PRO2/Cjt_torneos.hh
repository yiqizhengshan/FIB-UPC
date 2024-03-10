/** @file Cjt_torneos.hh
    @brief Especificación de la clase Cjt_torneos
*/

#ifndef _CJT_TORNEOS_HH_
#define _CJT_TORNEOS_HH_

#include "Torneo.hh"
#include "Cjt_jugadores.hh"
#include "Cjt_categorias.hh"
#ifndef NO_DIAGRAM
#include <map>
#include <iostream>
#include <cmath>
#endif
using namespace std;

/** @class Cjt_torneos
    @brief Representa un conjunto de torneos
*/
class Cjt_torneos
{
private:
    /** @brief Mapa de Torneos ordenado crecientemente por identificador*/
    map<string, Torneo> Torneos;

public:
    // Constructoras

    /** @brief Creadora por defecto.
    
        Se ejecuta automáticamente al declarar un conjunto de torneos.
        \pre <em>cierto</em>
        \post el resultado es un conjunto de torneos vacio
    */
    Cjt_torneos();

    // Consultoras

    /** @brief Consulta si existe el torneo.
    
        \pre <em>cierto</em>
        \post devuelve <em>true</em> si el torneo existe, <em>false</em> si no
    */
    bool existe(const string &t) const;

    // Modificadores

    /** @brief Añadir torneo al conjunto de torneos.
    
        \pre <em>cierto</em>
        \post se le añade un Torneo al parámetro implícito
    */
    bool afegir_torneo(int c, const string &t);

    /** @brief Dar de baja a un torneo del conjunto de torneos y actualizar el ranking.
    
        \pre <em>cierto</em>
        \post Si existe, se elimina un Torneo al parámetro implícito, se actualiza el ranking y devuelve true.
        Solo returna false al contrario.
    */
    bool baja_torneo(const string &t, Cjt_jugadores &jugadores);

    /** @brief Borrar los puntos que ha ganado el Tenista p
    
        \pre <em>cierto</em>
        \post Si existe, convierte a 0 los puntos ganados por el jugador <em>p</em> en cada Torneo
    */
    void baja_jugador_torneo(const string &p);

    /** @brief Inicialización de un torneo.
    
        \pre t existe en el conjunto de torneos
        \post confecciona e imprime el cuadro de emparejamientos de los jugadores inscritos
        del torneo t
    */
    void inicializar_torneo(const string &t, const Cjt_jugadores& jugadores);

    /** @brief Finalización de un torneo.
    
        \pre t existe en el conjunto de torneos y previamente se ha ejecutado el
        comando <em>inicializar_torneo</em>
        \post modifica e imprime el cuadro de emparejamiento/resultados del torneo t, se listan los puntos de cada jugador
        para el ranking, se actualiza el ranking y se actualizan las estadísticas de los jugadores
    */
    void finalizar_torneo(const string &t, const Cjt_categorias& categoria, Cjt_jugadores& jugadores);

    // Lectura/Escritura
    
    /** @brief Lectura de los nombres de las categorias.
    
        \pre T >= 0, T representa el número de pares de string t y enteros c que leeremos
        \post el parámetro implícito contiene el conjunto de torneos iniciales
    */
    void leer_torneos(int T);

    /** @brief Listar torneos.
    
        \pre <em>cierto</em>
        \post se listan, por orden creciente de identificador, el nombre y la
        categoría de cada torneo del conjunto de torneos
    */
    void listar_torneos(const Cjt_categorias &categoria) const;
};
#endif