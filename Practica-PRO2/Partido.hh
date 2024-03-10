/** @file Partido.hh
    @brief Especificación de la clase Partido
*/

#ifndef _PARTIDO_HH_
#define _PARTIDO_HH_

#ifndef NO_DIAGRAM
#include <iostream>
#include <vector>
#include <string>
#endif
using namespace std;

/** @class Partido
    @brief Representa un partido con atributos ganador y estadisticas del tenista del hijo izquierdo y derecho.
*/
class Partido
{
private:
    /** @brief Resultado del partido*/
    string resultado;

    /** @brief False si el ganador es el hijo izquierdo y True si es el hijo derecho*/
    bool winner;

    /** @brief Cantidad de juegos ganados por el hijo izquierdo*/
    int juegos_ganados_izq;

    /** @brief Cantidad de juegos perdidos por el hijo izquierdo*/
    int juegos_perdidos_izq;

    /** @brief Cantidad de sets ganados por el hijo izquierdo*/
    int sets_ganados_izq;

    /** @brief Cantidad de sets perdidos por el hijo izquierdo*/
    int sets_perdidos_izq;

    /** @brief Cantidad de juegos ganados por el hijo derecho*/
    int juegos_ganados_der;

    /** @brief Cantidad de juegos perdidos por el hijo derecho*/
    int juegos_perdidos_der;

    /** @brief Cantidad de sets ganados por el hijo derecho*/
    int sets_ganados_der;

    /** @brief Cantidad de sets perdidos por el hijo derecho*/
    int sets_perdidos_der;

public:
    //Constructoras

    /** @brief Creadora por defecto.
        
            Se ejecuta automáticamente al declarar un Partido.
            \pre <em>cierto</em>
            \post el resultado es un Partido vacio
    */
    Partido();

    // Consultoras

    /** @brief Consultar el resultado.
    
        \pre <em>cierto</em>
        \post el resultado es el resultado del parámetro implícito
    */
    string consultar_string() const;

    /** @brief Consultar ganador.
    
        \pre <em>cierto</em>
        \post el resultado es el ganador del Partido
    */
    bool consultar_winner() const;

    /** @brief Consultar juegos ganados del hijo izquierdo.
    
        \pre <em>cierto</em>
        \post el resultado son los juegos ganados del hijo izquierdo
    */
    int consultar_juegos_ganados_izq() const;

    /** @brief Consultar juegos perdidos del hijo izquierdo.
    
        \pre <em>cierto</em>
        \post el resultado son los juegos perdidos del hijo izquierdo
    */
    int consultar_juegos_perdidos_izq() const;

    /** @brief Consultar sets ganados del hijo izquierdo.
    
        \pre <em>cierto</em>
        \post el resultado son los sets ganados del hijo izquierdo
    */
    int consultar_sets_ganados_izq() const;

    /** @brief Consultar sets perdidos del hijo izquierdo.
    
        \pre <em>cierto</em>
        \post el resultado son los sets perdidos del hijo izquierdo
    */
    int consultar_sets_perdidos_izq() const;

    /** @brief Consultar juegos ganados del hijo derecho.
    
        \pre <em>cierto</em>
        \post el resultado son los juegos ganados del hijo derecho
    */
    int consultar_juegos_ganados_der() const;

    /** @brief Consultar juegos perdidos del hijo derecho.
    
        \pre <em>cierto</em>
        \post el resultado son los juegos perdidos del hijo derecho
    */
    int consultar_juegos_perdidos_der() const;

    /** @brief Consultar sets ganados del hijo derecho.
    
        \pre <em>cierto</em>
        \post el resultado son los sets ganados del hijo derecho
    */
    int consultar_sets_ganados_der() const;

    /** @brief Consultar sets perdidos del hijo derecho.
    
        \pre <em>cierto</em>
        \post el resultado son los sets perdidos del hijo derecho
    */
    int consultar_sets_perdidos_der() const;
    // Modificadoras

    /** @brief Calcular estadisticas del partido.
    
        \pre <em>cierto</em>
        \post el resultado es el ganador del partido apartir del resultado y modifica los atributos de la clase
    */
    bool calcular_stats();


    // Lectura/Escritura

    /** @brief Leer resultado.
    
        \pre <em>cierto</em>
        \post lee el string resultado
    */
    void leer();
};
#endif
