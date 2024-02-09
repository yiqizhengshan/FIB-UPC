(define (problem libros-extension-1-2)
    (:domain libros)
    (:objects
            l1 l2 l3 l4 l5 - libro
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
        
        (quiere_leer l2)
        (quiere_leer l4)
        
        (leido l1)

        (= (mes_leido l1) 0)
        (= (mes_leido l2) 0)
        (= (mes_leido l3) 0)
        (= (mes_leido l4) 0)
        (= (mes_leido l5) 0)

        (= (paginas l1) 500)
        (= (paginas l2) 200)
        (= (paginas l3) 300)
        (= (paginas l4) 300)
        (= (paginas l5) 500)

        (es_predecesor l1 l2)
        
        (es_paralelo l2 l3)
        (es_paralelo l3 l2)

        (es_paralelo l2 l4)
        (es_paralelo l4 l2)
        
        (es_paralelo l4 l3)
        (es_paralelo l3 l4)

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