package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idMedicament")
@JsonPropertyOrder({ "idMedicament", "nomMedicament", "nomGeneriqueMedicament", "dateExpiration", "dateEnregistrement", "description", "quantite", "prixUnitaire", "dosage" })
public class Medicament implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "ID_MEDICAMENT")
    @JsonProperty("idMedicament")
    private Long idMedicament;

    @Column(name = "NOM_MEDICAMENT")
    @JsonProperty("nomMedicament")
    private String nomMedicament;

    @Column(name = "NOM_GENERIQUE_MEDICAMENT")
    @JsonProperty("nomGeneriqueMedicament")
    private String nomGeneriqueMedicament;

    @Column(name = "DATE_EXPIRATION")
    @Temporal(TemporalType.DATE)
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("dateExpiration")
    private Date dateExpiration;

    @Column(name = "DATE_ENREGISTREMENT")
    @Temporal(TemporalType.DATE)
   // @JsonProperty("dateEnregistrement")
   // @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("dateEnregistrement")
    private Date dateEnregistrement;

    @Column(name = "FormePHARMACEUTIQUE")
    @JsonProperty("formePharmaceutique")
    private String formePharmaceutique;

    //@Column(name = "QUANTITE")
    @JsonProperty("quantiteDisponible")
    private Integer quantiteDisponible;

    @Column(name = "PRIX_UNITAIRE")
    @JsonProperty("prixUnitaire")
    private Integer prixUnitaire;

    @Column(name = "DOSAGE")
    @JsonProperty("dosage")
    private Integer dosage;


    private boolean supprimer=false;



  /*  @ManyToMany(mappedBy = "medicamentListConsultation")
    private List<Fiche_Consultation> fiche_consultationList;

    @ManyToMany(mappedBy = "medicamentListSuivie")
    private List<Fiche_Suivie> fiche_suivieList;*/

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "idInfirmiere")
    private Infirmiere infirmiere;

  public String getNomMedicament() {
      return nomMedicament;
    }

    /* @ManyToOne
    @JoinColumn(name = "facture_id")
    @JsonBackReference
    private Facture facture;*/

}
