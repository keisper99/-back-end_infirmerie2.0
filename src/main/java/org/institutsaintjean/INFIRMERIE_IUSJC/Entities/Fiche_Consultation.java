package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idFicheConsultation")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idFicheConsultation")

public class Fiche_Consultation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FICHE_CONSULTATION")
    @JsonProperty("idFicheConsultation")
    private Long idFicheConsultation;

    @Column(name = "DATE_CREATION_FC")
    @Temporal(TemporalType.DATE)
    private Date dateCreationFC;

    @Column(name = "HEURE_ARRIVEE_CONSULTATION")
    @Temporal(TemporalType.TIME)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date heureArriveeConsultation;



    @Column(name = "HEURE_SORTIE_CONSULTATION")
    @Temporal(TemporalType.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date heureSortieConsultation;


    @Column(name = "TEMPERATURE")
    private BigDecimal temperature;

    @Column(name = "POIDS")
        private BigDecimal poids;

    @Column(name = "TENSION")
    private BigDecimal tension;

    private String nouveauxSymptomes;
    private String nouveauxExamens;
    private String nouveauxDiagnostique;

    @Column(name = "HEURE_PROCHAIN_RENDEZ_VOUS")
    @Temporal(TemporalType.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date heureProchainRendezVous;


    @Column(name = "DATE_PROCHAIN_RENDEZ_VOUS")
    @Temporal(TemporalType.DATE)
    private Date dateProchainRendezVous;

    @Column(name = "SOINS_DISPENSE")
    private String soinsDispense;

    @Column(name = "VISIBLE")
    private boolean visible=true;

    //@JsonBackReference("etudiant-ficheConsultation")
    //@JsonIgnore
    //@JsonBackReference("etudiant-ficheConsultation")
   // @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(mappedBy = "ficheConsultation")
    private Facture facture;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "id_etudiant")
    //@JsonIgnoreProperties("fichesConsultation")
    private Etudiant etudiant;
    //@JsonManagedReference("ficheConsultation_ficheSuivie")
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "fiche_consultation")
    private List<Fiche_Suivie> fiche_suivieList;

 /*   @ManyToMany
    @JoinTable(name = "ficheConsultation_medicament",
            joinColumns = @JoinColumn(name = "ID_FICHE_CONSULTATION"),
            inverseJoinColumns = @JoinColumn(name = "idMedicament")
    )
    private List<Medicament> medicamentListConsultation;*/

    //@JsonIdentityReference(alwaysAsId = true)
    @ManyToMany
    @JoinTable(name = "ficheConsultation_medicament_prescrit",
            joinColumns = @JoinColumn(name = "idFicheConsultation"),
            inverseJoinColumns = @JoinColumn(name = "idMedicamentPrescrit")
    )
    private Set<MedicamentPrescrit> medicamentListConsultation;

    @ManyToMany
    @JoinTable(name = "ficheConsultation_symptome",
            joinColumns = @JoinColumn(name = "idFicheConsultation"),
            inverseJoinColumns = @JoinColumn(name = "idSymptome"))
    private Set<Symptome> symptomeList;

    @ManyToMany
    @JoinTable(name = "ficheConsultation_diagnostique",
            joinColumns = @JoinColumn(name = "idFicheConsultation"),
            inverseJoinColumns = @JoinColumn(name = "idDiagnostique"))
    private Set<Diagnostique> diagnostiqueList;

    @ManyToMany
    @JoinTable(name = "ficheConsultation_examen",
            joinColumns = @JoinColumn(name = "idFicheConsultation"),
            inverseJoinColumns = @JoinColumn(name = "idExamen"))
    private Set<Examen> examenList;

   // @JsonBackReference("ficheConsultation-infirmiere")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "idInfirmiere")
    private Infirmiere infirmiere;


}
