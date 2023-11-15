package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idMedicamentPrescrit")
public class MedicamentPrescrit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEDICAMENT_PRESCRIT")
    @JsonProperty("idMedicamentPrescrit")
    private Long idMedicamentPrescrit;

   // @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "ID_FICHE_CONSULTATION")
    //@JsonIdentityReference(alwaysAsId = true)
    @JsonIgnore
    private Fiche_Consultation ficheConsultation;

    @ManyToOne
    @JoinColumn(name = "ID_FICHE_SUIVIE")
    //@JsonIdentityReference(alwaysAsId = true)
    @JsonIgnore
    private Fiche_Suivie ficheSuivie;




    @ManyToOne
    @JoinColumn(name = "ID_MEDICAMENT")
   // @JsonManagedReference
    private Medicament medicament;

    @Column(name = "QUANTITE_PRESCRITE")
    private int quantitePrescrite;

    @Column(name = "POSOLOGIE")
    private String posogie;

    @ManyToOne
    @JoinColumn(name = "NumFacture")
    @JsonIdentityReference(alwaysAsId = true)
    private Facture facture;

    /*@ManyToOne
    @JoinColumn(name = "NumFacture")
    private Facture facture;*/

 /*   @ManyToMany(mappedBy = "medicamentPrescritsList")
    private List<Facture> factures;*/
}
