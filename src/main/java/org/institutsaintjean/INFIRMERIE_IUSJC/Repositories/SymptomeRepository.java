package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Symptome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SymptomeRepository extends JpaRepository<Symptome, Long> {
}
