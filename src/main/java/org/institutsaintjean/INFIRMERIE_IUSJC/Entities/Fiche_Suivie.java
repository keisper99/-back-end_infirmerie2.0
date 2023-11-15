package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idFicheSuivie")
public class Fiche_Suivie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("idFicheSuivie")
    @Column(name = "ID_FICHE_SUIVIE")
    private Long idFicheSuivie;

    @Column(name = "HEURE_PROCHAIN_RENDEZ_VOUS")
    @Temporal(TemporalType.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date heureProchainRendezVous;


    @Column(name = "DATE_PROCHAIN_RENDEZ_VOUS")
    @Temporal(TemporalType.DATE)
    private Date dateProchainRendezVous;

    @Column(name = "DATE_RENDEZ_VOUS")
    @Temporal(TemporalType.DATE)
    private Date dateRendezVous;

    @Column(name = "SOINS_DISPENSE")
    private String soinsDispense;

    @Column(name = "HEURE_ARRIVEE_SUIVIE")
    @Temporal(TemporalType.TIME)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date heureArriveeSuivie;

    @Column(name = "HEURE_SORTIE_SUIVIE")
    @Temporal(TemporalType.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date heureSortieSuivie;

    @Column(name = "TEMPERATURE")
    private BigDecimal temperature;

    @Column(name = "POIDS")
    private BigDecimal poids;

    @Column(name = "TENSION")
    private BigDecimal tension;

    private String nouveauxSymptomes;
    private String nouveauxExamens;
    private String nouveauxDiagnostique;

    @OneToOne(mappedBy = "ficheSuivie")
    private Facture facture;


    @JsonBackReference
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "idFicheConsultation")
    private Fiche_Consultation fiche_consultation;
    //@JsonBackReference
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "id_infirmiere")
    private Infirmiere infirmiere;

    /*@ManyToMany
    @JoinTable(name = "fiche_suivie_medicament",
            joinColumns = @JoinColumn(name = "ID_FICHE_CONSULTATION"),
            inverseJoinColumns = @JoinColumn(name = "idMedicament")
    )
    private List<Medicament> medicamentListSuivie;*/

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany
    @JoinTable(name = "ficheSuivie_medicament_prescrit",
            joinColumns = @JoinColumn(name = "idFicheSuivie"),
            inverseJoinColumns = @JoinColumn(name = "idMedicamentPrescrit")
    )
    private Set<MedicamentPrescrit> medicamentListSuivie;

    @ManyToMany
    @JoinTable(name = "ficheSuivie_symptome",
            joinColumns = @JoinColumn(name = "idFicheSuivie"),
            inverseJoinColumns = @JoinColumn(name = "idSymptome"))
    private Set<Symptome> symptomeList;

    @ManyToMany
    @JoinTable(name = "ficheSuivie_diagnostique",
            joinColumns = @JoinColumn(name = "idFicheSuivie"),
            inverseJoinColumns = @JoinColumn(name = "idDiagnostique"))
    private Set<Diagnostique> diagnostiqueList;

    @ManyToMany
    @JoinTable(name = "ficheSuivie_examen",
            joinColumns = @JoinColumn(name = "idFicheSuivie"),
            inverseJoinColumns = @JoinColumn(name = "idExamen"))
    private Set<Examen> examenList;



}
