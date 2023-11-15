package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Consultation;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Medicament;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.MedicamentPrescrit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicamentPrescritRepository extends JpaRepository<MedicamentPrescrit, Long> {
    List<MedicamentPrescrit> findByFicheConsultation(Fiche_Consultation ficheConsultation);


}
