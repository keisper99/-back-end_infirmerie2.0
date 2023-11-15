package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

    List<Medicament> findBysupprimerFalse();

    @Query("SELECT m FROM Medicament m WHERE m.quantiteDisponible <= 5")
    List<Medicament> findMedicamentsRuptureDeStock();

}
