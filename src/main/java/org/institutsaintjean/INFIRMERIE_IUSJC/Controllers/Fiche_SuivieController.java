
package org.institutsaintjean.INFIRMERIE_IUSJC.Controllers;

import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Fiche_Suivie_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Infirmiere_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.Fiche_SuivieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Infirmerie-IUSJC/Fiche_Suivie")
public class Fiche_SuivieController {

    @Autowired
    private Fiche_SuivieRepository ficheSuivieRepository;

    @Autowired
    private Fiche_Suivie_Metier ficheSuivieMetier;

    @GetMapping("/prochainsRendezVous/Liste")
    public List<Object[]> ListeProchainsRendezVous() {
        return ficheSuivieRepository.getProchainsRendezVous();
    }

    @GetMapping("/verifier/Rendez-Vous")
    public boolean verifierRendezVous(
            @RequestParam Date dateRendezVous,
            @RequestParam Date heureRendezVous) {

        List<Object[]> rendezVousList = ListeProchainsRendezVous();
        return ficheSuivieMetier.isRendezVousExists(rendezVousList,dateRendezVous,heureRendezVous);
    }

}

