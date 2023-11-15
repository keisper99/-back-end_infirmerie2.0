package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "NumFacture")
public class Facture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NumFacture")
    @JsonProperty("NumFacture")
    private Long NumFacture;


    //@Column(name = "statutFacture")
    private boolean statutFacture=false;

    @Column(name = "VISIBLE")
    private boolean visible=true;

    @Column(name = "MontantFacture")
    private Integer montantFacute;

    @Column(name = "dateCreationFacture")
    @Temporal(TemporalType.DATE)
    private Date dateCreationFacture;
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @OneToOne
    @JoinColumn(name = "ficheConsultation_id")
    private Fiche_Consultation ficheConsultation;


    @OneToOne
    @JoinColumn(name = "ficheSuivie_id")
    private Fiche_Suivie ficheSuivie;


/*  @ManyToMany
    @JoinTable(name = "facture_medicament",
            joinColumns = @JoinColumn(name = "NumFacture"),
            inverseJoinColumns = @JoinColumn(name = "idMedicament")
    )
    private List<Medicament> medicamentLists;*/

   /* @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL)
    private List<MedicamentPrescrit> medicamentPrescrits;*/

    @ManyToMany
    @JoinTable(name = "facture_medicament_prescrit",
            joinColumns = @JoinColumn(name = "NumFacture"),
            inverseJoinColumns = @JoinColumn(name = "idMedicamentPrescrit")
    )
    private List<MedicamentPrescrit> medicamentPrescritsList;

   /* @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Medicament> medicamentLists;*/



    // Constructeurs, méthodes, etc.

    public Long getNumFacture() {
        return NumFacture;
    }

    public Date getDateCreationFacture() {
        return dateCreationFacture;
    }




}
