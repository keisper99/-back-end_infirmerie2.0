package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Symptome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSymptome;

    @Column(nullable = false)
    private String nomSymptome;


}

