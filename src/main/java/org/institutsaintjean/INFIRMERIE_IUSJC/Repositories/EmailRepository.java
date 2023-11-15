package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailMessage, Long> {
}
