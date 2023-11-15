
package org.institutsaintjean.INFIRMERIE_IUSJC.Controllers;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Infirmiere;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Medicament;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Infirmiere_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Medicament_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.InfirmiereRepository;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Infirmerie-IUSJC/Medicament")

public class MedicamentController {
    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private Infirmiere_Metier infirmiere_metier;

    @Autowired
    private Medicament_Metier medicamentMetier;

    @Autowired
    private InfirmiereRepository infirmiereRepository;

    @GetMapping("/Liste")
    public List<Medicament> ListeMedicaments() {
        return medicamentMetier.getMedicamentsNonsupprimer();
    }

    @GetMapping("/Consulter/{idMedicament}")
    public Medicament AfficherEtudiant(@PathVariable Long idMedicament) {

        Medicament medicament = medicamentRepository.findById(idMedicament)
                .orElseThrow(() -> new IllegalArgumentException("Medicament non trouvée"));
        return medicament;
    }

    @PostMapping("/Ajout/{idInfirmiere}")
    public Medicament ajouterMedicament(@RequestParam(required = false) Long idMedicament,
                                        @PathVariable("idInfirmiere") Long idInfirmiere,
                                        @RequestBody Medicament medicament) {
        //Infirmiere infirmiere = infirmiere_metier.rechercherInfirmiereParId(idInfirmiere);
        System.out.println("belom jordan " + medicament.getNomMedicament());
        Infirmiere infirmiere = infirmiereRepository.findById(idInfirmiere)
                .orElseThrow(() -> new IllegalArgumentException("Infirmière non trouvée"));
        return infirmiere_metier.ajouterMedicament(idMedicament, infirmiere, medicament);
    }

    @PutMapping("/Supprimer/{idMedicament}")
    public Medicament supprimerEtudiant(@PathVariable Long idMedicament) {
        return medicamentMetier.supprimerMedicament(idMedicament);
    }

    @PutMapping("/Modifier/{idMedicament}")
    public ResponseEntity<Medicament> modifierMedicament(
            @PathVariable Long idMedicament,
            @RequestBody Medicament nouveauMedicament) {

        Medicament medicamentModifie = medicamentMetier.modifierMedicament(idMedicament, nouveauMedicament);

        return ResponseEntity.ok(medicamentModifie);
    }

    @GetMapping("/RuptureDeStock/Liste")
    public List<Medicament> getMedicamentsRuptureDeStock() {
        return medicamentRepository.findMedicamentsRuptureDeStock();
    }

    @PostMapping("/prescription/{id}/{quantitePrescrite}")
    public boolean prescrireMedicament(
            @PathVariable Long id,
            @PathVariable Integer quantitePrescrite
    ) {
      return medicamentMetier.peutPrescrireMedicament(id, quantitePrescrite);
      //false si la quatite de medicament disponble ne permet pas la prescription
        //true si elle permet la prescription
    }
}

