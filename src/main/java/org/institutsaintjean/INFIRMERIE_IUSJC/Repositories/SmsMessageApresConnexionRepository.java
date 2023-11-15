package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;

import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.SmsMessage;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.SmsMessageApresConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsMessageApresConnexionRepository extends JpaRepository<SmsMessageApresConnection, Long> {
}
