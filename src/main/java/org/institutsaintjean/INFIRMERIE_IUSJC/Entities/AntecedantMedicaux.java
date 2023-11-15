package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AntecedantMedicaux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAntecedantMedicaux;

    @Column(nullable = false)
    private String nomAntecedantMedicaux;
}
