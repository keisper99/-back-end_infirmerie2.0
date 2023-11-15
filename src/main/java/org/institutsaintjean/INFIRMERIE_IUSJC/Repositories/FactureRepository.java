package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Etudiant;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Facture;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    /*@Query("SELECT f FROM Facture f")
    List<Facture> findAllFactures();*/

    @Query("SELECT f, e.nom, e.prenom FROM Facture f " +
            "JOIN f.etudiant e " +
            "WHERE f.visible=true")
    List<Object[]> findAllFactures();



   /* @Query("SELECT f FROM Facture f WHERE f.statutFacture = :statutFacture")
    List<Facture> findFacturesByStatut(@Param("statutFacture") StatutFacture statutFacture);
*/


    @Query("SELECT f, e.nom, e.prenom,e.niveau,e.filiere FROM Facture f " +
            "JOIN f.etudiant e " +
            "WHERE f.statutFacture = false")
    List<Object[]> findBystatutFactureFalse();



    @Query("SELECT f, e.nom, e.prenom FROM Facture f " +
            "JOIN f.etudiant e " +
            "LEFT JOIN FETCH f.medicamentPrescritsList mp " +
            "LEFT JOIN FETCH mp.medicament " +
            "WHERE f.NumFacture = :NumFacture")
    Object[] findFactureWithMedicamentsPrescrits(@Param("NumFacture") Long NumFacture);

   /* Long findIdByFacture(Facture facture);
    @Query("SELECT f FROM Facture f WHERE f.etudiant.idEtudiant = :idEtudiant")
    List<Facture> findByidEtudiantFacture(@Param("idEtudiant") Long idEtudiant);

  *//*  @Query("SELECT f FROM Facture f WHERE f.ficheConsultation.idFicheConsultation = :id_FicheConsultation")
    List<Facture> findById_FicheConsultaionFacture(@Param("id_FicheConsultation") Long id_FicheConsultation);*//*

    @Query("SELECT f FROM Facture f WHERE f.ficheSuivie.idFicheSuivie = :id_FicheSuivie")
    List<Facture> findById_FicheSuivieFacture(@Param("id_FicheSuivie") Long id_FicheSuivie);

    @Query("SELECT f FROM Facture f WHERE f.etudiant.idEtudiant = :idEtudiant AND f.statutFacture = 'Non_Regle'")
    List<Facture> findFacturesNonRegleesByEtudiant(@Param("idEtudiant") Long idEtudiant);

    @Query("SELECT f FROM Facture f WHERE f.etudiant.idEtudiant = :idEtudiant AND f.statutFacture = 'Regle'")
    List<Facture> findFacturesRegleesByEtudiant(@Param("idEtudiant") Long idEtudiant);

    @Query("SELECT f FROM Facture f WHERE f.statutFacture = org.institutsaintjean.infirmerie.Entities.StatutFacture.Non_Regle")
    List<Facture> findFacturesNonReglees();

    @Query("SELECT f FROM Facture f WHERE f.statutFacture = org.institutsaintjean.infirmerie.Entities.StatutFacture.Regle")
    List<Facture> findFacturesReglees();

    @Query("SELECT f FROM Facture f")
    List<Facture> findAllFactures();

    @Query("SELECT DISTINCT f FROM Facture f " +
            "LEFT JOIN FETCH f.medicamentPrescritsList mp " +
            "LEFT JOIN FETCH mp.medicament " +
            "WHERE f.NumFacture = :idFacture")
    Facture findFactureWithMedicamentsPrescrits(@Param("idFacture") Long idFacture);*/

    List<Facture> findByEtudiant(Etudiant etudiant);


}
