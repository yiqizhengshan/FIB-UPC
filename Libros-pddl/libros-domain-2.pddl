(define (domain libros)
    (:requirements :strips :typing :fluents :adl) 
    (:types libro mes - object)
    (:predicates        
        (quiere_leer ?l - libro)
        (leido ?l - libro)
        (es_predecesor ?antes - libro ?despues - libro)
        (es_paralelo ?l1 - libro ?l2 - libro)
    )
    (:functions
        (paginas_disponibles_mes ?m - mes)
        (paginas ?l - libro)
        (numero_mes ?m - mes)
        (mes_leido ?l - libro)
    )

    (:action transformar
       ;miro todos los libros, si el libro esta en quiero leer, pongo tambien en quiero leer sus paralelos o predecesores
       :parameters (?l1 - libro ?l2 - libro)
       :precondition
                    (and
                        (quiere_leer ?l1)
                        (not (quiere_leer ?l2))
                        (or
                            ;caso predecesores
                            (es_predecesor ?l2 ?l1)
                            ;caso paralelos
                            (es_paralelo ?l2 ?l1)
                        )
                    )
       :effect (quiere_leer ?l2)
    )

    (:action leer ;paralelo o predecesor o mixto
        :parameters (?l - libro ?m - mes)
        :precondition (and  (quiere_leer ?l)
                            (not (leido ?l))
                            (>= (paginas_disponibles_mes ?m) (paginas ?l))
                            ;comprobar que se hagan todas las transformaciones paralelas
                            (not
                                (exists (?otro - libro)
                                    (and
                                        (es_paralelo ?otro ?l)
                                        (not (quiere_leer ?otro))
                                    )
                                )
                            )
                            (and
                                ; cumple condicion predecesor
                                ; si todos sus predecesores estan leidos y que el mes actual es superior a sus meses leidos, LEER
                                (forall (?otro - libro)
                                    (or
                                        (not (es_predecesor ?otro ?l))
                                        (and 
                                            (es_predecesor ?otro ?l)
                                            (leido ?otro)
                                            (> (numero_mes ?m) (mes_leido ?otro))
                                        )
                                    )
                                )
                                ; cumple condicion paralelo
                                (or
                                    ; si no tiene ningun libro paralelo leido, LEER
                                    (not 
                                        (exists (?otro - libro)
                                            (and 
                                                (es_paralelo ?otro ?l)
                                                (leido ?otro)
                                            )
                                        )
                                    )
                                    ; si para todos los paralelos leidos, el mes actual es antes, igual o despues, LEER
                                    (not
                                        (exists (?otro - libro)
                                            (and
                                                (es_paralelo ?otro ?l)
                                                (leido ?otro)
                                                (and
                                                    (not (= (- (numero_mes ?m) (mes_leido ?otro)) 1))
                                                    (not (= (- (mes_leido ?otro) (numero_mes ?m)) 1))
                                                    (not (= (- (numero_mes ?m) (mes_leido ?otro)) 0))
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                      )
        :effect (and
                    (leido ?l) 
                    (increase (mes_leido ?l) (numero_mes ?m))
                    (decrease (paginas_disponibles_mes ?m) (paginas ?l))
                )
    )
)