# Practica PRO2

<font size="4">Práctica de la asignatura de PRO2 en la FIB. </font>

<font size="4">Nota obtenida: 9.25</font>

## Ejecucion del programa

Para ejecutar el programa:

1. Compilar los archivos .cc para generar el .exe:

    ```sh
    sudo apt install make #si no tienes instalado make
    make
    ```
2. Para ejecutar el programa con un input de prueba y sacar tu propio input en :

    ```sh
    ./program.exe < sample.inp
    ```
3. Para limpiar todos los archivos generados:

    ```sh
    make clean
    ```
4. Para generar la documentación del programa:

    ```sh
    sudo apt install doxygen #si no tienes instalado doxygen
    doxygen Doxyfile 
    ```

## Explicación ``program.cc``
El programa principal lee varios parámetros de la entrada estándar, incluyendo el número de categorías, el número de torneos y el número de jugadores. Luego entra en un bucle donde lee un comando de la entrada estándar y realiza la operación correspondiente. El bucle continúa hasta que se introduce el comando `fin`.

Los comandos disponibles son:

- `nuevo_jugador` o `nj`: Añade un nuevo jugador.
- `nuevo_torneo` o `nt`: Añade un nuevo torneo.
- `baja_jugador` o `bj`: Elimina un jugador.
- `baja_torneo` o `bt`: Elimina un torneo.
- `iniciar_torneo` o `it`: Inicia un torneo.
- `finalizar_torneo` o `ft`: Termina un torneo.
- `listar_ranking` o `lr`: Lista el ranking de jugadores.
- `listar_jugadores` o `lj`: Lista todos los jugadores.
- `consultar_jugador` o `cj`: Muestra información sobre un jugador.
- `listar_torneos` o `lt`: Lista todos los torneos.
- `listar_categorias` o `lc`: Lista todas las categorías.

## Hecho por

Yiqi Zheng - [@yiqizhengshan](https://github.com/yiqizhengshan)

Link del proyecto: [https://github.com/yiqizhengshan/FIB-UPC/tree/main/Practica-PRO2](https://github.com/yiqizhengshan/FIB-UPC/tree/main/Practica-PRO2)

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.