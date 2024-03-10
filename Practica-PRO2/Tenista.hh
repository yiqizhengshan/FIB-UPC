/** @file Tenista.hh
    @brief Especificación de la clase Tenista
*/

#ifndef _TENISTA_HH_
#define _TENISTA_HH_

#include "Partido.hh"
#ifndef NO_DIAGRAM
#include <iostream>
#endif
using namespace std;

/** @class Tenista
    @brief Representa un tenista con atributos puntos, posicion y varias estadisticas
*/
class Tenista
{
private:
    /** @brief Cantidad de puntos que tiene el parámetro implícito*/
    int puntos;

    /** @brief Posicion en la que se encuentra el parámetro implícito en el ránking*/
    int posicion;

    /** @brief Cantidad de torneos jugados que tiene el parámetro implícito*/
    int torneos_jugados;

    /** @brief Cantidad de juegos ganados que tiene el parámetro implícito*/
    int juegos_ganados;

    /** @brief Cantidad de juegos perdidos que tiene el parámetro implícito*/
    int juegos_perdidos;

    /** @brief Cantidad de sets ganados que tiene el parámetro implícito*/
    int sets_ganados;

    /** @brief Cantidad de sets perdidos que tiene el parámetro implícito*/
    int sets_perdidos;

    /** @brief Cantidad de partidos ganados que tiene el parámetro implícito*/
    int partidos_ganados;

    /** @brief Cantidad de partidos perdidos que tiene el parámetro implícito*/
    int partidos_perdidos;

public:
    // Constructoras

    /** @brief Creadora por defecto.
    
        Se ejecuta automáticamente al declarar un Tenista.
        \pre <em>cierto</em>
        \post el resultado es un Tenista vacio
    */
    Tenista();

    /** @brief Creadora con posicion.
    
        \pre <em>cierto</em>
        \post el resultado es un Tenista con posicion p
    */
    Tenista(int p);

    // Consultoras

    /** @brief Consultora de puntos.
    
        \pre <em>cierto</em>
        \post el resultado son los puntos del parámetro implícito
    */
    int consultar_puntos() const;

    /** @brief Consultora de posicion.
    
        \pre <em>cierto</em>
        \post el resultado es la posicion en ranking del parámetro implícito
    */
    int consultar_posicion() const;

    /** @brief Consultora de torneos jugados.
    
        \pre <em>cierto</em>
        \post el resultado son los torneos jugados del parámetro implícito
    */
    int consultar_torneos_jugados() const;

    /** @brief Consultora de juegos ganados.
    
        \pre <em>cierto</em>
        \post el resultado son los juegos ganados del parámetro implícito
    */
    int consultar_juegos_ganados() const;

    /** @brief Consultora de juegos perdidos.
    
        \pre <em>cierto</em>
        \post el resultado son los juegos perdidos del parámetro implícito
    */
    int consultar_juegos_perdidos() const;

    /** @brief Consultora de sets ganados.
    
        \pre <em>cierto</em>
        \post el resultado son los sets ganados del parámetro implícito
    */
    int consultar_sets_ganados() const;

    /** @brief Consultora de sets perdidos.
    
        \pre <em>cierto</em>
        \post el resultado son los sets perdidos del parámetro implícito
    */
    int consultar_sets_perdidos() const;

    /** @brief Consultora de partidos ganados.
    
        \pre <em>cierto</em>
        \post el resultado son los partidos ganados del parámetro implícito
    */
    int consultar_partidos_ganados() const;

    /** @brief Consultora de partidos perdidos.
    
        \pre <em>cierto</em>
        \post el resultado son los partidos perdidos del parámetro implícito
    */
    int consultar_partidos_perdidos() const;

    // Modificadores

    /** @brief Sumadora de puntos.
    
        \pre <em>cierto</em>
        \post el parámetro implícito pasa a tener "puntos + n" cantidad de puntos
    */
    void sumar_puntos(int n);

    /** @brief >Restadora de puntos.
    
        \pre <em>cierto</em>
        \post el parámetro implícito pasa a tener "puntos - n" cantidad de puntos
    */
    void restar_puntos(int n);

    /** @brief Modificadora de posicion.
    
        \pre <em>cierto</em>
        \post el parámetro implícito pasa a tener posicion "n"
    */
    void modif_posicion(int n);

    /** @brief Modificadora de torneos jugados.
    
        \pre <em>cierto</em>
        \post se le suma uno al numero de torneos jugados
    */
    void modif_torneos_jugados();

    /** @brief Modificadora de juegos ganados.
    
        \pre <em>cierto</em>
        \post el parámetro implícito pasa a tener juegos_ganados "n"
    */
    void modif_juegos_ganados(int n);

    /** @brief Modificadora de juegos perdidos.
    
        \pre <em>cierto</em>
        \post el parámetro implícito pasa a tener juegos_perdidos "n"
    */
    void modif_juegos_perdidos(int n);

    /** @brief Modificadora de sets ganados.
    
        \pre <em>cierto</em>
        \post el parámetro implícito pasa a tener sets_ganados "n"
    */
    void modif_sets_ganados(int n);

    /** @brief Modificadora de sets perdidos.
    
        \pre <em>cierto</em>
        \post el parámetro implícito pasa a tener sets_perdidos "n"
    */
    void modif_sets_perdidos(int n);

    /** @brief Modificadora de partidos ganados.
    
        \pre <em>cierto</em>
        \post el parámetro implícito pasa a tener partidos_ganados "n"
    */
    void modif_partidos_ganados(int n);
    
    /** @brief Modificadora de partidos perdidos.
    
        \pre <em>cierto</em>
        \post el parámetro implícito pasa a tener partidos_perdidos "n"
    */
    void modif_partidos_perdidos(int n);

    /** @brief Actualizar estadísticas del jugador.
    
        \pre <em>cierto</em>
        \post el parámetro implícito actualiza sus atributos según Partido, ganador y dre
    */
    void actualizar_stats(const Partido &p, bool ganador, bool dre);

    // Lectura/Escritura

    /** @brief Escribir datos de Tenista.
    
        \pre <em>cierto</em>
        \post se escriben todos los datos del tenista
    */
    void escribir_tenista() const;
};
#endif