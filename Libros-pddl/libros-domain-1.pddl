(define (domain libros)
    (:requirements :strips :typing :fluents :adl)
    (:types libro mes - object)
    (:predicates
        (quiere_leer ?l - libro)
        (leido ?l - libro)
        (es_predecesor ?antes - libro ?despues - libro)
    )
    (:functions
        (paginas_disponibles_mes ?m - mes)
        (paginas ?l - libro)
        (numero_mes ?m - mes)
        (mes_leido ?l - libro)
    )

    (:action transformar
        ;miro todos los libros, si el libro esta en quiero leer, pongo tambien en quiero leer sus predecesores
        :parameters (?l1 - libro ?l2 - libro)
        :precondition
            (and
                (quiere_leer ?l1)
                (not (quiere_leer ?l2))
                (es_predecesor ?l2 ?l1)
            )
        :effect (quiere_leer ?l2)
    )

    (:action leer ;predecesores
        :parameters (?l - libro ?m - mes)
        :precondition
            (and
                (quiere_leer ?l)
                (not (leido ?l))
                (>= (paginas_disponibles_mes ?m) (paginas ?l))
                ; si todos sus predecesores estan leidos y que el mes actual es superior a sus meses leidos, LEER
                (forall
                    (?otro - libro)
                    (or
                        (not (es_predecesor ?otro ?l))
                        (and
                            (es_predecesor ?otro ?l)
                            (leido ?otro)
                            (> (numero_mes ?m) (mes_leido ?otro))
                        )
                    )
                )
            )
        :effect
            (and
                (leido ?l)
                (increase (mes_leido ?l) (numero_mes ?m))
                (decrease (paginas_disponibles_mes ?m) (paginas ?l))
            )
    )
)