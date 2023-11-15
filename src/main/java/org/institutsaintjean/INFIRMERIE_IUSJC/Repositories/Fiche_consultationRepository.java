package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Etudiant;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Consultation;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Suivie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface Fiche_consultationRepository extends JpaRepository<Fiche_Consultation, Long> {
    Fiche_Consultation findByIdFicheConsultation(Long idFicheConsultation);

    @Query("SELECT f, e.nom, e.prenom,e.filiere,e.niveau FROM Fiche_Consultation f " +
            "JOIN f.etudiant e " +
            "LEFT JOIN FETCH f.medicamentListConsultation mp " +
            "LEFT JOIN FETCH mp.medicament " +
            "LEFT JOIN FETCH f.symptomeList sl " +
            "LEFT JOIN FETCH f.diagnostiqueList dl " +
            "LEFT JOIN FETCH f.examenList el " +
            "WHERE f.idFicheConsultation = :idFicheConsultation")
    Object[] findFicheConsultationById(@Param("idFicheConsultation") Long idFicheConsultation);

    @Query("SELECT f, e.nom, e.prenom FROM Fiche_Consultation f " +
            "JOIN f.etudiant e " +
            "LEFT JOIN FETCH f.medicamentListConsultation mp " +
            "LEFT JOIN FETCH mp.medicament " +
            "LEFT JOIN FETCH f.symptomeList sl " +
            "LEFT JOIN FETCH f.diagnostiqueList dl " +
            "LEFT JOIN FETCH f.examenList el " +
            "WHERE f.etudiant.idEtudiant = :idEtudiant")
    List<Object[]> findFichesConsultationByEtudiantId(@Param("idEtudiant") Long idEtudiant);

    @Query("SELECT f, e.nom, e.prenom FROM Fiche_Consultation f " +
            "JOIN f.etudiant e " +
            "WHERE f.visible=true")
    List<Object[]> findAllFichesConsultation();

    @Query("SELECT f, e.nom, e.prenom FROM Facture f " +
            "JOIN f.etudiant e")
    List<Object[]> findAllFactures();



    @Query("SELECT f, e.nom, e.prenom FROM Facture f " +
            "JOIN f.etudiant e " +
            "LEFT JOIN FETCH f.medicamentPrescritsList mp " +
            "LEFT JOIN FETCH mp.medicament " +
            "WHERE mp.ficheConsultation.idFicheConsultation = :idFicheConsultation")
    List<Object[]> findFacturesByFicheConsultationId(@Param("idFicheConsultation") Long idFicheConsultation);

    @Query("SELECT YEAR(fc.dateCreationFC), COUNT(fc) " +
            "FROM Fiche_Consultation fc " +
            "WHERE YEAR(fc.dateCreationFC) = :Annee " +
            "GROUP BY YEAR(fc.dateCreationFC)")
    List<Object[]> NbrConsultationParAnnee(@Param("Annee") int Annee);



    @Query("SELECT a.annee, a.mois, SUM(a.nombreConsultations) " +
            "FROM (" +
            "    SELECT YEAR(fc.dateCreationFC) AS annee, " +
            "           DATE_FORMAT(fc.dateCreationFC, '%M') AS mois, " +
            "           COUNT(fc) AS nombreConsultations " +
            "    FROM Fiche_Consultation fc " +
            "    WHERE YEAR(fc.dateCreationFC) = :annee " +
            "    GROUP BY YEAR(fc.dateCreationFC), MONTH(fc.dateCreationFC), DATE_FORMAT(fc.dateCreationFC, '%M')" +
            ") a " +
            "GROUP BY a.annee, a.mois")
    List<Object[]> NbrConsultationParAnneeEtMois(@Param("annee") int annee);




    @Query("SELECT fd.nomDiagnostique, COUNT(fc) AS nombreFichesConsultation " +
            "FROM Fiche_Consultation fc " +
            "JOIN fc.diagnostiqueList fd " +
            "GROUP BY fd.nomDiagnostique")
    List<Object[]> countFichesConsultationByDiagnostique();

    @Query("SELECT fs FROM Fiche_Consultation fc JOIN fc.fiche_suivieList fs WHERE fc.idFicheConsultation = :idFicheConsultation")
    List<Fiche_Suivie> findFichesSuiviesByFicheConsultationId(@Param("idFicheConsultation") Long idFicheConsultation);

    @Query(value = "SELECT COUNT(*) " +
            "FROM fiche_consultation " +
            "WHERE DATE(DATE_CREATION_FC) = CURDATE()",
            nativeQuery = true)
    Long countConsultationsForToday();

    List<Fiche_Consultation> findByEtudiant(Etudiant etudiant);



}
