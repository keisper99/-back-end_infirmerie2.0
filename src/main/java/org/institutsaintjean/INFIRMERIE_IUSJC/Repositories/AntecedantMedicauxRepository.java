package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.AntecedantMedicaux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository

public interface AntecedantMedicauxRepository extends JpaRepository<AntecedantMedicaux, Long> {
    boolean existsByNomAntecedantMedicaux(String nomAntecedantMedicaux);
    @Query("SELECT a FROM AntecedantMedicaux a WHERE a.nomAntecedantMedicaux = :nom")
    AntecedantMedicaux findByNomAntecedantMedicaux(@Param("nom") String nom);
}
