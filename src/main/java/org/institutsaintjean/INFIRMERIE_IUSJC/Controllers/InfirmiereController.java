


package org.institutsaintjean.INFIRMERIE_IUSJC.Controllers;

import net.sf.jasperreports.engine.JRException;

import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Consultation;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Suivie;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Infirmiere_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Rapport_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.InfirmiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Infirmerie-IUSJC/Infirmiere")
public class InfirmiereController {
    @Autowired
    private Infirmiere_Metier infirmiere_metier;

    @Autowired
    private Rapport_Metier rapportMetier;








   /* @PostMapping(value = "/Ajouter/FicheSuivie/{id_Infirmiere}", consumes = "application/json")
    public Fiche_Suivie creerFicheSuivie(@PathVariable("id_Infirmiere") Long idInfirmiere,
                                         @RequestBody Fiche_Suivie ficheSuivie,
                                         @RequestParam(required = false) List<Long> idMedicament,
                                         @RequestParam(required = false) List<Long> idSymptome,
                                         @RequestParam(required = false) List<Long> idExamen,
                                         @RequestParam(required = false) List<String> nouveauxSymptomes,
                                         @RequestParam(required = false) List<String> nouveauxExamens) {

        return  infirmiere_metier.creerFicheSuivie(idInfirmiere, ficheSuivie, idMedicament, idSymptome, idExamen, nouveauxSymptomes, nouveauxExamens);
    }*/

    @PostMapping(value = "/Ajouter/FicheConsultation/{id_Etudiant}/{id_Infirmiere}", consumes = "application/json")
    public Fiche_Consultation creerFicheConsultation(@PathVariable("id_Etudiant") Long idEtudiant,
                                                     @PathVariable("id_Infirmiere") Long idInfirmiere,
                                                     @RequestBody Fiche_Consultation ficheConsultation,
                                                     @RequestParam(required = false) List<Long> idMedicament,
                                                     @RequestParam(required = false) List<Long> idSymptome,
                                                     @RequestParam(required = false) List<Long> idExamen,
                                                     @RequestParam(required = false) List<Long> idDiagnostique,
                                                     @RequestParam(required = false) List<Integer> quantiteMedicamentPrescrite,
                                                     @RequestParam(required = false)  List<String> posologieList,
                                                     @RequestParam(required = false) List<String> nouveauxSymptomes,
                                                     @RequestParam(required = false) List<String> nouveauxExamens,
                                                     @RequestParam(required = false) List<String> nouveauxDiagnostique

    ) throws JRException, IOException {
       return infirmiere_metier.creerFicheConsultation(idEtudiant,idInfirmiere, ficheConsultation, idMedicament, idSymptome, idExamen, idDiagnostique,quantiteMedicamentPrescrite,posologieList ,nouveauxSymptomes, nouveauxExamens,nouveauxDiagnostique);
    }



    @GetMapping("/Imprimer/Facture/{numFacture}")
    public String exportReport(@PathVariable("numFacture") Long numFacture, @RequestParam(defaultValue = "pdf") String reportFormat) throws IOException, JRException {
        return rapportMetier.exportReport(numFacture,reportFormat);
    }

}




