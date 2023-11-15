package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;

import lombok.RequiredArgsConstructor;

import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Medicament;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class Medicament_MetierImplementation implements Medicament_Metier{

    @Autowired
    MedicamentRepository medicamentRepository;


    @Override
    public Medicament supprimerMedicament(Long idMedicament) {
        Medicament medicament = medicamentRepository.findById(idMedicament).get();
        if(medicament==null) throw new RuntimeException("Medicament introuvable");
        medicament.setSupprimer(true);
        return medicamentRepository.save(medicament);

    }

    public List<Medicament> getMedicamentsNonsupprimer() {
        return medicamentRepository.findBysupprimerFalse();
    }

    public Medicament modifierMedicament(Long idMedicament, Medicament nouveauMedicament) {
        Optional<Medicament> medicamentOptional = medicamentRepository.findById(idMedicament);

        Medicament ancienMedicament = medicamentOptional.get();


        ancienMedicament.setNomMedicament(nouveauMedicament.getNomMedicament());
        ancienMedicament.setNomGeneriqueMedicament(nouveauMedicament.getNomGeneriqueMedicament());
        ancienMedicament.setDateExpiration(nouveauMedicament.getDateExpiration());
        ancienMedicament.setFormePharmaceutique(nouveauMedicament.getFormePharmaceutique());
        ancienMedicament.setQuantiteDisponible(nouveauMedicament.getQuantiteDisponible());
        ancienMedicament.setPrixUnitaire(nouveauMedicament.getPrixUnitaire());
        ancienMedicament.setDosage(nouveauMedicament.getDosage());
        ancienMedicament.setDateEnregistrement(new Date());


        return medicamentRepository.save(ancienMedicament);
    }

    public boolean peutPrescrireMedicament(Long idMedicamentPrescrit, Integer quantitePrescrite) {

        Medicament medicament = medicamentRepository.findById(idMedicamentPrescrit).orElse(null);

        if (medicament != null) {
            return medicament.getQuantiteDisponible() >= quantitePrescrite;
        }

        return false;
    }



}
