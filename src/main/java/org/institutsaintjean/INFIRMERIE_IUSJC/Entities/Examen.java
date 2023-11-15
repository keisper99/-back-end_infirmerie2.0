package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExamen;

    @Column(nullable = false)
    private String nomExamen;
}
