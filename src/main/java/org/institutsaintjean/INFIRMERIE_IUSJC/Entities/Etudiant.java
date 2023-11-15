package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

//import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idEtudiant")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idEtudiant")
public class Etudiant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ETUDIANT")
    @JsonProperty("idEtudiant")
    private Long idEtudiant;

    @Column(name = "MATRICULE", length = 10)
    private String matricule;


    private String nouveauxAntecedantsMedicaux;

    @Column(name = "NOM", length = 100)
    private String nom;

    @Column(name = "NOM_CONTACT_URGENCE", length = 100)
    private String nomContactUrgence;

    @Column(name = "RELATION_CONTACT_URGENCE", length = 100)
    private String relationContactUrgence;

    @Column(name = "PRENOM", length = 100)
    private String prenom;

    @Column(name = "SEXE")
    private String sexe;

    @Column(name = "NIVEAU")
    private Integer niveau;

    @Column(name = "FILIERE")
    private String filiere;

    @Column(name = "DATE_DE_NAISSANCE")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateDeNaissance;

    @Column(name = "NUMERO_DE_TELEPHONE")
    private Long numeroDeTelephone;

    @Column(name = "NUMERO_DE_TELEPHONE_URGENCE")
    private Long numeroDeTelephoneUrgence;

    @Column(name = "NUMERO_WHATSAPP")
    private Long numeroWhatsapp;

    @Column(name = "EMAILEtudiant", length = 1000)
    private String emailEtudiant;

    @Column(name = "EMAILParent", length = 1000)
    private String emailContactUrgence;

    @Column(name = "POIDS")
    private BigDecimal poids;

    @Column(name = "TAILLE")
    private BigDecimal taille;

    @Column(name = "GOUPE_SANGUIN")
    private String groupeSanguin;


    @Column(name = "BLOQUE", length = 10)
    private boolean bloque=false;
   // @JsonManagedReference("etudiant-ficheConsultation")
   // @JsonIgnore
   // @JsonManagedReference("etudiant-ficheConsultation")
    //@JsonIgnoreProperties("etudiant")
   //@JsonIdentityReference(alwaysAsId = true)
   @OneToMany(mappedBy = "etudiant")
   private List<Facture> factures;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "etudiant")
    private List<Fiche_Consultation> fichesConsultation;

    //JsonBackReference("etudiant-infirmiere")
    //@JsonIgnore
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "id_infirmiere")
    private Infirmiere infirmiere;
    @ManyToMany
    @JoinTable(name = "etudiant_antecedantMedicaux",
            joinColumns = @JoinColumn(name = "idEtudiant"),
            inverseJoinColumns = @JoinColumn(name = "idAntecedantMedicaux"))
    private List<AntecedantMedicaux> antecedantMedicauxList;

    public String getMatricule() {
        return matricule;
    }

    public String getNom() {
        return nom;
    }
}
