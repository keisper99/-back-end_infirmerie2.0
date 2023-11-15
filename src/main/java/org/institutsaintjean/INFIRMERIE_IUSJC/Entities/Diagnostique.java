package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Diagnostique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiagnostique;

    @Column(nullable = false)
    private String nomDiagnostique;

}
