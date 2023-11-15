package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.*;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class Etudiant_Metier_implementation implements Etudiant_Metier {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private InfirmiereRepository infirmiereRepository;
    @Autowired
    private AntecedantMedicauxRepository antecedantMedicauxRepository;

    @Autowired
    private Fiche_consultationRepository ficheConsultationRepository;

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private Fiche_SuivieRepository ficheSuivieRepository;

    @Override
    public Etudiant ajouterEtudiant(Long idInfirmiere, Etudiant etudiant, List<Long> antecedantMedicauxIds, List<String> nouveauxAntecedantsMedicaux) {
        Infirmiere infirmiere = infirmiereRepository.findById(idInfirmiere)
                .orElseThrow(() -> new IllegalArgumentException("Infirmiere non trouvée"));

        etudiant.setInfirmiere(infirmiere);


        // Vérifier et initialiser la liste des antécédents médicaux s'il est null
        if (etudiant.getAntecedantMedicauxList() == null) {
            etudiant.setAntecedantMedicauxList(new ArrayList<>());
        }

        if (antecedantMedicauxIds != null && !antecedantMedicauxIds.isEmpty()) {
            List<AntecedantMedicaux> antecedantMedicauxList = antecedantMedicauxRepository.findAllById(antecedantMedicauxIds);
            etudiant.setAntecedantMedicauxList(antecedantMedicauxList);
        }

        if (nouveauxAntecedantsMedicaux != null && !nouveauxAntecedantsMedicaux.isEmpty()) {
            for (String nouvelAntecedantMedicaux : nouveauxAntecedantsMedicaux) {
                boolean antecedantExiste = antecedantMedicauxRepository.existsByNomAntecedantMedicaux(nouvelAntecedantMedicaux);
                if (!antecedantExiste) {
                    AntecedantMedicaux nouvelAntecedant = new AntecedantMedicaux();
                    nouvelAntecedant.setNomAntecedantMedicaux(nouvelAntecedantMedicaux);
                    AntecedantMedicaux antecedantMedicaux = antecedantMedicauxRepository.save(nouvelAntecedant);
                    etudiant.getAntecedantMedicauxList().add(antecedantMedicaux);
                }
            }
        }

        return etudiantRepository.save(etudiant);
    }


    @Override
    public Etudiant modifierEtudiant(Long idEtudiant, Etudiant etudiantModifie, List<Long> antecedantMedicauxIds, List<String> nouveauxAntecedantsMedicaux) {
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new IllegalArgumentException("Etudiant non trouvé"));

        etudiant.setMatricule(etudiantModifie.getMatricule());
        etudiant.setNom(etudiantModifie.getNom());
        etudiant.setPrenom(etudiantModifie.getPrenom());
        etudiant.setSexe(etudiantModifie.getSexe());
        etudiant.setDateDeNaissance(etudiantModifie.getDateDeNaissance());
        etudiant.setEmailEtudiant(etudiantModifie.getEmailEtudiant());
        etudiant.setEmailContactUrgence(etudiantModifie.getEmailContactUrgence());
        etudiant.setRelationContactUrgence(etudiantModifie.getRelationContactUrgence());
        etudiant.setNumeroWhatsapp(etudiantModifie.getNumeroWhatsapp());
        etudiant.setNumeroDeTelephone(etudiantModifie.getNumeroDeTelephone());
        etudiant.setNumeroDeTelephoneUrgence(etudiantModifie.getNumeroDeTelephoneUrgence());
        etudiant.setNomContactUrgence(etudiantModifie.getNomContactUrgence());
        etudiant.setNiveau(etudiantModifie.getNiveau());
        etudiant.setFiliere(etudiantModifie.getFiliere());
        etudiant.setPoids(etudiantModifie.getPoids());
        etudiant.setTaille(etudiantModifie.getTaille());
        etudiant.setGroupeSanguin(etudiantModifie.getGroupeSanguin());


        // Vérifier et initialiser la liste des antécédents médicaux s'il est null
        if (etudiant.getAntecedantMedicauxList() == null) {
            etudiant.setAntecedantMedicauxList(new ArrayList<>());
        }

        if (antecedantMedicauxIds != null && !antecedantMedicauxIds.isEmpty()) {
            List<AntecedantMedicaux> antecedantMedicauxList = antecedantMedicauxRepository.findAllById(antecedantMedicauxIds);
            etudiant.setAntecedantMedicauxList(antecedantMedicauxList);
        }

        if (nouveauxAntecedantsMedicaux != null && !nouveauxAntecedantsMedicaux.isEmpty()) {
            for (String nouvelAntecedantMedicaux : nouveauxAntecedantsMedicaux) {
                boolean antecedantExiste = antecedantMedicauxRepository.existsByNomAntecedantMedicaux(nouvelAntecedantMedicaux);
                if (!antecedantExiste) {
                    AntecedantMedicaux nouvelAntecedant = new AntecedantMedicaux();
                    nouvelAntecedant.setNomAntecedantMedicaux(nouvelAntecedantMedicaux);
                    AntecedantMedicaux antecedantMedicaux = antecedantMedicauxRepository.save(nouvelAntecedant);
                    etudiant.getAntecedantMedicauxList().add(antecedantMedicaux);
                }
            }
        }

        return etudiantRepository.save(etudiant);
    }



    @Override
    public Etudiant rechercherEtudiant(Long idEtudiant) {
        Etudiant etudiant = etudiantRepository.findById(idEtudiant).get();
        if(etudiant==null) throw new RuntimeException("Etudiant introuvable");
        return etudiant;
    }


    @Override
    public Etudiant supprimerEtudiant(Long idEtudiant) {
        Etudiant etudiant= rechercherEtudiant(idEtudiant);
        if(etudiant.isBloque()) throw new RuntimeException("L'etudiant n'existe pas");
        etudiant.setBloque(true);

        // Masquez également toutes les fiches de consultation de l'étudiant
        List<Fiche_Consultation> fichesConsultation = ficheConsultationRepository.findByEtudiant(etudiant);
        fichesConsultation.forEach(fiche -> fiche.setVisible(false));

        List<Facture> factures = factureRepository.findByEtudiant(etudiant);
        factures.forEach(facture -> facture.setVisible(false));

        return etudiantRepository.save(etudiant);
    }

    public List<Etudiant> getEtudiantsNonBloques() {
        return etudiantRepository.findByBloqueFalse();
    }
}
