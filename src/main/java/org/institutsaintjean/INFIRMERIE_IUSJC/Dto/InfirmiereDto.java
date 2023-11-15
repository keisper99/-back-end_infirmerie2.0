


package org.institutsaintjean.INFIRMERIE_IUSJC.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfirmiereDto {

    private Long idInfirmiere;
    private String nom;
    private String prenom;
    private String login;
    private String token;

}



