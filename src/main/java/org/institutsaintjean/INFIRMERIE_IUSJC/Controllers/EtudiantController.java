
package org.institutsaintjean.INFIRMERIE_IUSJC.Controllers;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.AntecedantMedicaux;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Etudiant;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Etudiant_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.AntecedantMedicauxRepository;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Infirmerie-IUSJC")
public class EtudiantController {
    @Autowired
    private Etudiant_Metier etudiant_metier;

    @Autowired
    private AntecedantMedicauxRepository antecedantMedicauxRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @PostMapping(value = "/Etudiant/Ajouter/{id_Infirmiere}", consumes = "application/json")
    public Etudiant ajouterEtudiant(@PathVariable("id_Infirmiere") Long idInfirmiere,
                                    @RequestBody Etudiant etudiant,
                                    @RequestParam(required = false) List<Long> antecedantMedicauxList,
                                    @RequestParam(required = false) List<String> nouveauxAntecedantsMedicaux) {
        return etudiant_metier.ajouterEtudiant(idInfirmiere, etudiant, antecedantMedicauxList, nouveauxAntecedantsMedicaux);
    }


    @PutMapping("/Etudiant/Modifier/{idEtudiant}")
    public ResponseEntity<Etudiant> modifierEtudiant(@PathVariable("idEtudiant") Long idEtudiant,
                                                     @RequestBody Etudiant etudiantModifie,
                                                     @RequestParam(required = false) List<Long> antecedantMedicauxList,
                                                     @RequestParam(required = false) List<String> nouveauxAntecedantsMedicaux) {
        Etudiant etudiant = etudiant_metier.modifierEtudiant(idEtudiant, etudiantModifie,antecedantMedicauxList,nouveauxAntecedantsMedicaux);
        return ResponseEntity.ok(etudiant);
    }

    @PutMapping("/Etudiant/Supprimer/{id}")
    public Etudiant supprimerEtudiant(@PathVariable Long id) {
       return etudiant_metier.supprimerEtudiant(id);
    }

    @GetMapping("/Etudiant/Listes")
    public List<Etudiant> getEtudiantsNonBloques() {
        return etudiant_metier.getEtudiantsNonBloques();
    }

    @GetMapping("/ListeAntecedantMedicaux")
    public List<AntecedantMedicaux> ListeAntecedantMedicaux() {
        return antecedantMedicauxRepository.findAll();
    }

    @GetMapping("/Etudiant/Consulter/{idEtudiant}")
    public Etudiant AfficherEtudiant(@PathVariable Long idEtudiant) {

        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new IllegalArgumentException("Etudiant non trouvée"));
        return etudiant;
    }
}

