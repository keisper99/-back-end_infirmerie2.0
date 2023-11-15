
package org.institutsaintjean.INFIRMERIE_IUSJC.Controllers;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Facture;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Facture_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Infirmerie-IUSJC/Facture")
public class FactureController {

    @Autowired
    private Facture_Metier factureMetier;


    @Autowired
    private FactureRepository factureRepository;

    @PutMapping("RegleFacture/{NumFacture}")
    public Facture RegleFacture(@PathVariable Long NumFacture) {
        return factureMetier.RegleFacture(NumFacture);
    }

    @GetMapping("/Liste")
    public List<Object[]> getFactures() {
        return factureRepository.findAllFactures();
    }



    @GetMapping("/non-reglees")
    public List<Object[]> getFacturesNonReglees() {
        return factureRepository.findBystatutFactureFalse();
    }

    @GetMapping("Consulter/{NumFacture}")
    public Object[] getFactureWithMedicamentsPrescrits(@PathVariable Long NumFacture) {
        Object[] result = factureRepository.findFactureWithMedicamentsPrescrits(NumFacture);
        return result;
    }


  /*  @Autowired
    private FactureRepository factureRepository;

    @GetMapping("/factures/{etudiantId}/etudiant")
    public List<Facture> getFacturesByIdEtudiant(@PathVariable("etudiantId") Long idEtudiant) {
        return factureRepository.findByidEtudiantFacture(idEtudiant);
    }


  @GetMapping("/factures/{id_FicheConsultation}/FicheConsultation")
    public List<Facture> getFacturesByIdFicheConsultation(@PathVariable("id_FicheConsultation") Long id_FicheConsultation) {
        return factureRepository.findById_FicheConsultaionFacture(id_FicheConsultation);
    }


    @GetMapping("/factures/{id_FicheSuivie}/FicheConsultation")
    public List<Facture> getFacturesByIdFicheSuivie(@PathVariable("id_FicheSuivie") Long id_FicheSuivie) {
        return factureRepository.findById_FicheSuivieFacture(id_FicheSuivie);
    }

    @GetMapping("/non-reglees/{etudiantId}")
    public List<Facture> getFacturesNonRegleesEtudiant(@PathVariable("etudiantId") Long etudiantId) {
        return factureRepository.findFacturesNonRegleesByEtudiant(etudiantId);
    }

    @GetMapping("/reglees/{etudiantId}")
    public List<Facture> getFacturesRegleesEtudiant(@PathVariable("idEtudiant") Long idEtudiant) {
        return factureRepository.findFacturesRegleesByEtudiant(idEtudiant);
    }

    @GetMapping("/{idFacture}")
    public Facture getFactureWithMedicamentsPrescrits(@PathVariable Long idFacture) {
        Facture facture = factureRepository.findFactureWithMedicamentsPrescrits(idFacture);
        return facture;
    }

    @GetMapping("/reglees")
    public List<Facture> getFacturesReglees() {
        return factureRepository.findFacturesReglees();
    }

    @GetMapping("/Nonreglees")
    public List<Facture> getFacturesNonReglees() {
        return factureRepository.findFacturesNonReglees();
    }

    @GetMapping("/Liste")
    public List<Facture> getFactures() {
        return factureRepository.findAllFactures();
    }
*/


}

