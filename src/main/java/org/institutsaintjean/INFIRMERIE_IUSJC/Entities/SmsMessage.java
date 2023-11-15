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
public class SmsMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MESSAGE_DIRECT")
    private Long idMessageDirect;

    private String message;
    private Long numero;
    private Date dateProchainRendezVous;
}
