package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class Fiche_Suivie_Metier_Implementation implements Fiche_Suivie_Metier{

    public boolean isRendezVousExists(List<Object[]> rendezVousList, Date dateRendezVous, Date heureRendezVous) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        for (Object[] rendezVous : rendezVousList) {
            Date rendezVousDate = (Date) rendezVous[5];
            Date rendezVousHeure = (Date) rendezVous[4];

            String rendezVousDateFormate = dateFormat.format(rendezVousDate );
            String rendezVousHeureFormate = timeFormat.format(rendezVousHeure);

            // Comparaison de la date et de l'heure du rendez-vous
            if (rendezVousDateFormate.equals(dateFormat.format(dateRendezVous)) && rendezVousHeureFormate.equals(timeFormat.format(heureRendezVous) )) {
                return true; // Rendez-vous trouvé
            }
        }
        return false; // Aucun rendez-vous correspondant trouvé
    }


}
