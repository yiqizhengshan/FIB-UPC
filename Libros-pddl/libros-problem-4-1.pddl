(define (problem libros-extension-3-1)
    (:domain libros)
    (:objects
            l1 l2 l3 l4 l5 l6 l7 l8 l9 l10 l11 - libro
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
        (= (mes_leido l10) 0)
        (= (mes_leido l11) 0)

        (leido l11)
        (quiere_leer l4)
        (quiere_leer l7)
        (quiere_leer l8)

        (es_predecesor l11 l2)

        (es_predecesor l1 l2)
        (es_predecesor l1 l3)
        (es_predecesor l1 l4)

        (es_predecesor l2 l7)

        (es_predecesor l3 l5)
        (es_predecesor l3 l6)

        (es_predecesor l5 l7)

        (es_predecesor l6 l7)
        (es_predecesor l6 l8)

        (es_predecesor l7 l9)
        (es_predecesor l7 l10)

        (es_paralelo l6 l5)
        (es_paralelo l5 l6)

        (es_paralelo l4 l7)
        (es_paralelo l7 l4)

        (= (paginas l1) 500)
        (= (paginas l2) 200)
        (= (paginas l3) 100)
        (= (paginas l4) 530)
        (= (paginas l5) 350)
        (= (paginas l6) 450)
        (= (paginas l7) 340)
        (= (paginas l8) 300)
        (= (paginas l9) 550)
        (= (paginas l10) 270)
        (= (paginas l11) 600)
    )

    (:goal	
	    (forall (?l - libro)
            (or 
                (and (quiere_leer ?l) (leido ?l))
                (and (not (quiere_leer ?l)) (not (leido ?l)))
            )
        )
    )
)