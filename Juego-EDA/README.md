# Juego EDA

<font size="4">Práctica de la asignatura de EDA en la FIB. </font>

<font size="4">Nota obtenida: 9.34</font>

## Ejecucion del programa

Para ejecutar el programa:

1. Primero copiarse AIDummy y Board en la version de vuestro sistema:

    ```sh
    #Si estamos en Linux
    cp AIDummy.o.Linux64 AIDummy.o
    cp Board.o.Linux64 Board.o
    ```

2. Compilar los archivos .cc y generar el .exe:

    ```sh
    sudo apt install make #si no tienes instalado make
    make all
    ```
3. Para realizar una partida con mi jugador, de nombre Dummy2:

    ```sh
    ./Game Dummy2 Dummy Dummy Dummy -s 1234 -i default.cnf -o default.res
    ```
    - `-s seed` set random seed
    - `-i input` set input file
    - `-o output` set output file

4. Para poder observar la partida, abrir `viewer.html` que se encuentra en la carpeta Viewer y dentro, seleccionar el archivo de output `default.res`.

5. Para limpiar todos los archivos generados:

    ```sh
    make clean
    ```

6. Si queremos realizar muchas partidas:

    ```sh
    ./GameTest.sh Dummy2 Dummy Dummy Dummy N
    ```
    - `N` número de partidas que se quiere realizar
    - Al final del script, se borran todos los output generados

## Explicación ``AIDummy2.cc``
Este código es una "IA" para un juego llamado `The Walking Dead` que fue creado por el profesor `Albert Oliveras` para la asignatura de `Estructura de Dades i Algorismes` de la FIB. Podéis consultar la API en `api.pdf`.

## Hecho por

Yiqi Zheng - [@yiqizhengshan](https://github.com/yiqizhengshan)

Link del proyecto: [https://github.com/yiqizhengshan/FIB-UPC/tree/main/Juego-EDA](https://github.com/yiqizhengshan/FIB-UPC/tree/main/Juego-EDA)

## License

Distributed under the MIT License. See `LICENSE` for more information.