package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface Fiche_Suivie_Metier {
    public boolean isRendezVousExists(List<Object[]> rendezVousList, Date dateRendezVous, Date heureRendezVous);

}
