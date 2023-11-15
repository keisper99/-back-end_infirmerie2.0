package org.institutsaintjean.INFIRMERIE_IUSJC.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicamentPrescritDTO {

    private String nomMedicament;
    private Integer prixUnitaire;
    private Integer quantitePrescrite;
}
