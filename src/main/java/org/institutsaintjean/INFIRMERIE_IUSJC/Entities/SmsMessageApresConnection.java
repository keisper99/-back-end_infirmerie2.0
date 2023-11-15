package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SmsMessageApresConnection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MESSAGE_APRES_CONNEXION")
    private Long idMessageApresConnexion;

    private String message;
    private Long numero;
    private Date dateProchainRendezVous;

    public SmsMessageApresConnection(SmsMessage smsMessage){
        this.message=smsMessage.getMessage();
        this.dateProchainRendezVous=smsMessage.getDateProchainRendezVous();
        this.numero=smsMessage.getNumero();
    }
}
