(define (problem libros-extension-2-1)
    (:domain libros)
    (:objects
            l1 l2 l3 l4 l5 l6 - libro
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

        (= (paginas l1) 800)
        (= (paginas l2) 800)
        (= (paginas l3) 800)
        (= (paginas l4) 800)
        (= (paginas l5) 800)
        (= (paginas l6) 800)

        (quiere_leer l1)
        (quiere_leer l2)
        (quiere_leer l3)
        (quiere_leer l4)
        (quiere_leer l5)
        (quiere_leer l6)

        (es_paralelo l1 l2)
        (es_paralelo l2 l1)

        (es_paralelo l3 l4)
        (es_paralelo l4 l3)

        (es_paralelo l5 l1)
        (es_paralelo l1 l5)

        (es_paralelo l6 l3)
        (es_paralelo l3 l6)

    )

    (:goal	
	    (and (forall (?l - libro) (or 
                                    (and (quiere_leer ?l) (leido ?l))
                                    (and (not (quiere_leer ?l)) (not (leido ?l)))
                                  )
             )
        )
    )
)