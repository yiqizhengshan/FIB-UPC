# Libros pddl

<font size="4">Tercera práctica de la asignatura de IA en la FIB. </font>

<font size="4">Nota obtenida: 11 </font>

## Dominios
El fichero libros-domain-1.pddl contiene el dominio con la implementacion basica + extension 1 + extension 3. Los juegos de prueba que se pueden ejecutar con él son los libros-problem 1-1, 1-2, 2-1, 2-2.

El fichero libros-domain-2.pddl contiene el dominio con la implementacion de todas las extensiones. Todos los juegos de prueba se pueden ejecutar.

## Juegos de prueba
Cada problem representa un juego de prueba dirigido a un cierto nivel o extension del problema, que son los siguientes:
- Problem 1: nivel basico (+ extension 3 limitada)
- Problem 2: basico + extension 1 (+ extension 3 limitada)
- Problem 3: basico + extension 1 + extension 2 (+ extension 3 limitada)
- Problem 4: basico + extension 1 + extension 2 + extension 3

## Ejecucion del programa
Para ejecutar el código, hay que estar en la carpeta con el código fuente del Metric-FF y ejecutar (conviene que los ficheros .pddl esten dentro de esta carpeta):

```sh
./ff -O -o <path a dominio> -f <path a problema>
```
- <path a dominio> tiene la forma: ./Libros-pddl/libros-domain-1.pddl
- <path a problema> tiene la forma: ./Libros-pddl/libros-problem-1-1.pddl

Ejemplo:

```sh
./ff -O -o Libros-pddl/libros-domain.pddl -f Libros-pddl/libros-problem-1-1.pddl
```

## Explicación ``generador.cc``
Para obtener un archivo de prueba aleatorio ejecuta:

```sh
g++ -o exe generador.cc
```

Se generará un fichero (o se sobreescribirá) output.pddl que servirá como el libros-problem.pddl

Para generar la aleatoriedad, el ``generador.cc`` tiene una funcion que genera numeros aleatorios, para ajustar la probabilidad de generar relaciones de libros paralelos, relaciones de libros predecesores, el numero de libros que se quieren leer, las paginas de cada libro... Basta con incrementar o decrementar los valores que se pasan a la funcion de generarNumeroAleatorio, cuanto mayor sea el intervalo menor sera la probabilidad de que suceda lo que se quiere.

Por defecto hemos puesto que el número de libros que se generan sea entre 1 y 15 ya que como el intervalo de generar el número de páginas de cada libro dará una media de alrededor de 400 páginas y cada mes tiene capacidad de 800 páginas, a partir de 15 libros ya se generan ficheros de prueba cuya solución puede no ser posible con mayor probabilidad.

Para probar problemas con dominios de dimensiones altas, se deberían poner las paginas de cada libro a 0 para trabajar entonces solo con la extensión dos, ajustando en el ``generador.cc`` todos los parámetros que fueran conveninetes para incrementar el dominio del problema

## Hecho por

Jordi Muñoz - [@jordimunozflorensa](https://github.com/jordimunozflorensa)

Jianing Xu - [@jianingxu1](https://github.com/jianingxu1)

Yiqi Zheng - [@yiqizhengshan](https://github.com/yiqizhengshan)

<br>

Link del proyecto: [https://github.com/yiqizhengshan/Libros-pddl](https://github.com/yiqizhengshan/Libros-pddl)

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.