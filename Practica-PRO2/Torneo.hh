/** @file Torneo.hh
    @brief Especificación de la clase Torneo
*/

#ifndef _TORNEO_HH_
#define _TORNEO_HH_

#include "Cjt_jugadores.hh"
#include "Cjt_categorias.hh"
#include "Partido.hh"
#ifndef NO_DIAGRAM
#include <iostream>
#include <map>
#include "BinTree.hh"
#include <string>
#endif
using namespace std;

/** @class Torneo
    @brief Representa un torneo con atributo categoria, primera edicion, altura y numero de jugadores
*/
class Torneo
{
private:
    /** @brief Struct con atributo nombre, puntos ganados y si se le han añadido los puntos*/
    struct Inscrito{
        string nombre;
        int puntos;
        bool dado_puntos;
    };

    /** @brief Número de a que categoría pertenece*/
    int categoria;

    /** @brief Vector de Inscritos anteriores para guardar los puntos ganados*/
    vector<Inscrito> cjt_jugadores_anteriores;

    /** @brief Vector de Inscritos actuales*/
    vector<Inscrito> cjt_jugadores_inscritos;

    /** @brief Árbol de emparejamientos donde el valor del nodo indica la posición en el que se encuentra el Inscrito en cjt_jugadores_inscritos*/
    BinTree<int> cuadro_emparejamiento;

    /** @brief Indica si es primera edicion(true) o no(false)*/
    bool primera_edicion;

    /** @brief Número que indica el número de jugadores que participan en el Torneo*/
    int num_jugadores;

    /** @brief Funcion estática,recursiva e inmersiva que modifica el cuadro de emparejamientos una vez dado los resultados*/
    static void i_confeccionar_cuadro_resultados(BinTree<int> &cuadro, int &numero, vector<Partido> &resultados, Cjt_jugadores &jugadores, const vector<Inscrito> &inscritos);

    /** @brief Funcion estática,recursiva e inmersiva que crea el cuadro de emparejamientos*/
    static BinTree<int> i_confeccionar_cuadro_emparejamiento(int v, int n, int altura, int altura_max, int pot);

    /** @brief Funcion estática y recursiva que escribe el cuadro de emparejamientos de iniciar torneo*/
    static void write(const BinTree<int> &a, const vector<Inscrito> &v);

    /** @brief Funcion estática y recursiva que escribe el cuadro de emparejamientos de finalizar torneo*/
    static void write_2(const BinTree<int> &a, vector<Inscrito> &v, const vector<Partido> &resultados, const Cjt_categorias &categorias, int &numero, int altura, int categoria);

public:
    //Constructoras
    
    /** @brief Creadora por defecto.
    
        Se ejecuta automáticamente al declarar un Torneo.
        \pre <em>cierto</em>
        \post el resultado es un Torneo vacio
    */
    Torneo();

    /** @brief Creadora con categoría concreta.
    
        \pre 1 <= categoria <= C
        \post el resultado es un Torneo con categoría <em>categoría</em>
    */
    Torneo(int categoria);

    // Consultoras

    /** @brief Consultora de categoria.
    
        \pre <em>cierto</em>
        \post el resultado es la categoría del parámetro implícito
    */
    int consultar_categoria() const;

    /** @brief Consultora de primer vez.
    
        \pre <em>cierto</em>
        \post el resultado indica si es primera edicion
    */
    bool consultar_primera_edicion() const;


    //Modificadoras

    /** @brief Actualizar jugadores del torneo.
    
        \pre <em>cierto</em>
        \post el conjunto de jugadores inscritos se convierte en el conjunto de jugadores anteriores
    */
    void actualizar_jugadores_anteriores();

    /** @brief Actualizar la edicion del torneo.
    
        \pre <em>cierto</em>
        \post el torneo pasa a no ser de primera edicion
    */
    void actualizar_edicion();

    /** @brief Quitar puntos del torneo.
    
        \pre <em>cierto</em>
        \post al conjunto de jugadores anteriores se le quitan los respectivos puntos ganados, en el
        Torneo implícito, del ranking.
    */
    void quitar_puntos(Cjt_jugadores &jugadores);

    /** @brief Sumar puntos del torneo.
    
        \pre <em>cierto</em>
        \post al conjunto de jugadores anteriores se le suman los respectivos puntos ganados, en el
        Torneo implícito, del ranking.
    */
    void sumar_puntos(Cjt_jugadores &jugadores);

    /** @brief Poner puntos ganados a 0.
    
        \pre <em>cierto</em>
        \post si el Tenista p existe en el conjunto de jugadores anteriores, modifica los puntos ganados a 0
    */
    void eliminar_jugador(const string &p);

    // Lectura/Escritura

    /** @brief Cofeccionar cuadro de emparejamiento.
    
        \pre <em>cierto</em>
        \post se confecciona el cuadro de emparejamiento del parámetro implícito
    */
    void confeccionar_cuadro_emparejamiento(int n, int altura_max);

    /** @brief Confeccionar cuadro de resultados
    
        \pre <em>cierto</em>
        \post se leen los resultados, se confecciona el cuadro de resultados del parámetro implícito y se actualizan las estadísticas
        de cada jugador
    */
    void confeccionar_cuadro_resultados(Cjt_jugadores& jugadores, vector<Partido> &resultados);

    /** @brief Escritura del cuadro de emparejamiento.
    
        \pre <em>cierto</em>
        \post se escribe el cuadro de emparejamiento del parámetro implícito
    */
    void escribir_cuadro_emparejamiento() const;

    /** @brief Consultora del cuadro de resultados.
    
        \pre <em>cierto</em>
        \post se escribe el cuadro de resultados del parámetro implícito
    */
    void escribir_cuadro_resultados(const Cjt_categorias &categorias, Cjt_jugadores &jugadores, vector<Partido> &resultados);

    /** @brief Lectura del conjunto de jugadores inscritos.
    
        \pre <em>cierto</em>
        \post el parámetro implícito contiene el conjunto de jugadores inscritos
    */
    void leer_jugadores_inscritos(int n, const Cjt_jugadores &jugadores);

};
#endif