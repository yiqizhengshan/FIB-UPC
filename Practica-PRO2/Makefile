OPCIONS = -D_JUDGE_ -D_GLIBCXX_DEBUG -O2 -Wall -Wextra -Werror -Wno-sign-compare -std=c++11

program.exe: program.o Cjt_categorias.o Cjt_jugadores.o Cjt_torneos.o Tenista.o Torneo.o Partido.o
	g++ -o program.exe program.o Cjt_categorias.o Cjt_jugadores.o Cjt_torneos.o Tenista.o Torneo.o Partido.o

Cjt_categorias.o:  Cjt_categorias.cc Cjt_categorias.hh
	g++ -c Cjt_categorias.cc $(OPCIONS)

Cjt_jugadores.o:  Cjt_jugadores.cc Cjt_jugadores.hh
	g++ -c Cjt_jugadores.cc $(OPCIONS)

Cjt_torneos.o:  Cjt_torneos.cc Cjt_torneos.hh
	g++ -c Cjt_torneos.cc $(OPCIONS)

Tenista.o:  Tenista.cc Tenista.hh
	g++ -c Tenista.cc $(OPCIONS)

Torneo.o:  Torneo.cc Torneo.hh
	g++ -c Torneo.cc $(OPCIONS)

Partido.o: Partido.cc Partido.hh
	g++ -c Partido.cc $(OPCIONS)

program.o: program.cc
	g++ -c program.cc $(OPCIONS)

clean:
	rm *.o
	rm *.exe
	rm *.tar

practica.tar: Cjt_categorias.cc Cjt_categorias.hh Cjt_jugadores.cc Cjt_jugadores.hh Cjt_torneos.cc Cjt_torneos.hh Tenista.cc Tenista.hh Torneo.cc Torneo.hh Partido.cc Partido.hh program.cc Makefile html.zip
	tar -cvf practica.tar Cjt_categorias.cc Cjt_categorias.hh Cjt_jugadores.cc Cjt_jugadores.hh Cjt_torneos.cc Cjt_torneos.hh Tenista.cc Tenista.hh Torneo.cc Torneo.hh Partido.cc Partido.hh program.cc Makefile html.zip