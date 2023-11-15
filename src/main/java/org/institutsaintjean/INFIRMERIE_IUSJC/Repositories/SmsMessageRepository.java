package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;

import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.SmsMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsMessageRepository extends JpaRepository<SmsMessage, Long> {
}
