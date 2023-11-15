
package org.institutsaintjean.INFIRMERIE_IUSJC.Controllers;


import net.sf.jasperreports.engine.JRException;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Consultation;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Suivie;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Infirmiere_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.Fiche_consultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/Infirmerie-IUSJC/Fiche_consultation")
public class Fiche_ConsultationController {


    @Autowired
    private Fiche_consultationRepository ficheConsultationRepository;

    @Autowired
    private Infirmiere_Metier infirmiere_metier;

    @GetMapping("Consulter/{idFicheConsultation}")
    public Object[] findFicheConsultationById(@PathVariable("idFicheConsultation") Long idFicheConsultation) {
      return ficheConsultationRepository.findFicheConsultationById(idFicheConsultation);
    }

    @GetMapping("/Etudiant/{idEtudiant}")
    public List<Object[]> findFichesConsultationByEtudiantId(@PathVariable("idEtudiant") Long idEtudiant) {
       return ficheConsultationRepository.findFichesConsultationByEtudiantId(idEtudiant);
    }

    @GetMapping("/Liste")
    public List<Object[]> getListeFicheConsultation() {
        return ficheConsultationRepository.findAllFichesConsultation();
    }

    @GetMapping("/Factures/{idFicheConsultation}")
    public List<Object[]> getFacturesByFicheConsultationId(@PathVariable("idFicheConsultation") Long idFicheConsultation) {
        return ficheConsultationRepository.findFacturesByFicheConsultationId(idFicheConsultation);
    }

    @GetMapping("/NbrConsultationParAnnee/{annee}")
    public List<Object[]> getConsultationsByYear(@PathVariable("annee") int Annee) {
        return ficheConsultationRepository.NbrConsultationParAnnee(Annee);
    }


    @GetMapping("/NbrConsultationParAnneeEtMois/{annee}")
    public List<Object[]> getConsultationsByYearAndMonth(@PathVariable("annee") int annee) {
        return ficheConsultationRepository.NbrConsultationParAnneeEtMois(annee);
    }

    @GetMapping("/NombreDeCasParDiagnostique")
    public List<Object[]> NombreDeCasParDiagnostique() {
        return ficheConsultationRepository.countFichesConsultationByDiagnostique();
    }

    @GetMapping("/Nombre/Du/Jour")
    public Long NombreConsultationsDuJour() {
        return ficheConsultationRepository.countConsultationsForToday();
    }

    @GetMapping("/Liste/fiches-suivies/{idFicheConsultation}")
    public ResponseEntity<List<Fiche_Suivie>> getFichesSuiviesByFicheConsultation(@PathVariable Long idFicheConsultation) {
        Optional<Fiche_Consultation> ficheConsultation = ficheConsultationRepository.findById(idFicheConsultation);

        if (ficheConsultation.isPresent() && ficheConsultation.get().isVisible()) {
            List<Fiche_Suivie> fichesSuivies = ficheConsultation.get().getFiche_suivieList();
            return ResponseEntity.ok(fichesSuivies);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/Ajouter/FicheSuivie/{idFicheConsultation}/{id_Infirmiere}", consumes = "application/json")
    public Fiche_Suivie creerFicheSuivie(@PathVariable("idFicheConsultation") Long idFicheConsultation,
                                         @PathVariable("id_Infirmiere") Long idInfirmiere,
                                         @RequestBody Fiche_Suivie fiche_suivie,
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
        return infirmiere_metier.creerFicheSuivie(idFicheConsultation,idInfirmiere, fiche_suivie, idMedicament, idSymptome, idExamen, idDiagnostique,quantiteMedicamentPrescrite,posologieList ,nouveauxSymptomes, nouveauxExamens,nouveauxDiagnostique);
    }
}



