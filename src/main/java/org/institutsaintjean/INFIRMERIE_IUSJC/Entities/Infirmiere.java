package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idInfirmiere")
public class Infirmiere implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("idInfirmiere")
    private Long idInfirmiere;


    @Column(name = "nom", length = 100)
    private String nom;

    @Column(name = "prenom", length = 100)
    private String prenom;

    @Column(name = "password",nullable = false)
    @Size(max = 100)
    private String password;
    @Column(nullable = false,name = "login")
    @Size(max = 100)
    private String login;






  /*  @PrePersist
    private void initLogin() {
        this.login = this.nom; // Utilisez la valeur du nom pour initialiser le login
    }*/

    //@JsonManagedReference("etudiant-infirmiere")
    //@JsonIgnore
    //@JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "infirmiere")
    private List<Etudiant> etudiantList;
    //@JsonManagedReference
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "infirmiere")
    private List<Fiche_Consultation> fiche_consultationList;
    //@JsonManagedReference
    //@JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "infirmiere",cascade = CascadeType.ALL)
    private List<Fiche_Suivie> fiche_suivieList;
    //@JsonManagedReference
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "infirmiere")
    private List<Medicament> medicamentListInfirmiere;


}
