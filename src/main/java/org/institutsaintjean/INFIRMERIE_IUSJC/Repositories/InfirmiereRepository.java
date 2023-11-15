package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;



import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Infirmiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface InfirmiereRepository extends JpaRepository<Infirmiere, Long> {
    Optional<Infirmiere> findByLogin(String login);
}
