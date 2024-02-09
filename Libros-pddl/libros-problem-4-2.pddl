(define (problem libros-extension-3-2)
    (:domain libros)
    (:objects
            l1 l2 l3 l4 l5 l6 l7 l8 l9 - libro
            enero febrero marzo abril mayo junio julio agosto septiembre octubre noviembre diciembre - mes
    )

    (:init
        (= (numero_mes enero) 1)
        (= (numero_mes febrero) 2)
        (= (numero_mes marzo) 3)
        (= (numero_mes abril) 4)
        (= (numero_mes mayo) 5)
        (= (numero_mes junio) 6)
        (= (numero_mes julio) 7)
        (= (numero_mes agosto) 8)
        (= (numero_mes septiembre) 9)
        (= (numero_mes octubre) 10)
        (= (numero_mes noviembre) 11)
        (= (numero_mes diciembre) 12)

        (= (paginas_disponibles_mes enero) 800)
        (= (paginas_disponibles_mes febrero) 800)
        (= (paginas_disponibles_mes marzo) 800)
        (= (paginas_disponibles_mes abril) 800)
        (= (paginas_disponibles_mes mayo) 800)
        (= (paginas_disponibles_mes junio) 800)
        (= (paginas_disponibles_mes julio) 800)
        (= (paginas_disponibles_mes agosto) 800)
        (= (paginas_disponibles_mes septiembre) 800)
        (= (paginas_disponibles_mes octubre) 800)
        (= (paginas_disponibles_mes noviembre) 800)
        (= (paginas_disponibles_mes diciembre) 800)

        (= (mes_leido l1) 0)
        (= (mes_leido l2) 0)
        (= (mes_leido l3) 0)
        (= (mes_leido l4) 0)
        (= (mes_leido l5) 0)
        (= (mes_leido l6) 0)
        (= (mes_leido l7) 0)
        (= (mes_leido l8) 0)
        (= (mes_leido l9) 0)

        (= (paginas l1) 800)
        (= (paginas l2) 100)
        (= (paginas l3) 500)
        (= (paginas l4) 700)
        (= (paginas l5) 500)
        (= (paginas l6) 300)
        (= (paginas l7) 200)
        (= (paginas l8) 200)
        (= (paginas l9) 400)

        (quiere_leer l6)
        (quiere_leer l2)


        (es_predecesor l3 l4)
        (es_predecesor l4 l5)
        (es_predecesor l5 l6)

        (es_predecesor l7 l8)
        (es_predecesor l8 l9)
        
        (es_paralelo l1 l2)
        (es_paralelo l2 l1)

        (es_paralelo l3 l7)
        (es_paralelo l7 l3)

        (es_paralelo l3 l8)
        (es_paralelo l8 l3)

        (es_paralelo l5 l9)
        (es_paralelo l9 l5)
        
    )

    (:goal	
	    (and (forall (?l - libro)
                (or 
                    (and (quiere_leer ?l) (leido ?l))
                    (and (not (quiere_leer ?l)) (not (leido ?l)))
                )
             )
        )
    )
)