package org.institutsaintjean.INFIRMERIE_IUSJC.Repositories;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Etudiant;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Consultation;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Suivie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface Fiche_SuivieRepository extends JpaRepository<Fiche_Suivie, Long> {

/*
    @Query("SELECT e.nom, e.prenom, e.filiere, e.niveau, fs.heureProchainRendezVous, fs.dateProchainRendezVous " +
            "FROM Fiche_Suivie fs " +
            "JOIN fs.fiche_consultation fc " +
            "JOIN fc.etudiant e " +
            "WHERE fs.dateProchainRendezVous >= CURRENT_DATE " +
            "ORDER BY fs.dateProchainRendezVous ASC, fs.heureProchainRendezVous ASC")
    List<Object[]> getProchainsRendezVous();
*/

    @Query(value = "SELECT e.nom, e.prenom, e.filiere, e.niveau, " +
            "       fs.heure_prochain_rendez_vous AS heureRendezVous, " +
            "       fs.date_prochain_rendez_vous AS dateRendezVous, " +
            "       d.nom_diagnostique AS DiagnostiquesSuivi, " +
            "FROM fiche_suivie fs " +
            "JOIN fiche_consultation fc ON fs.id_fiche_consultation = fc.id_fiche_consultation " +
            "JOIN diagnostique d ON fs.id_diagnostique = d.id_diagnostique " +
            "JOIN etudiant e ON fc.id_etudiant = e.id_etudiant " +
            "WHERE fs.date_prochain_rendez_vous >= CURRENT_DATE " +
            "UNION " +
            "SELECT e.nom, e.prenom, e.filiere, e.niveau, " +
            "       fc.heure_prochain_rendez_vous AS heureRendezVous, " +
            "       fc.date_prochain_rendez_vous AS dateRendezVous " +
            "       d.nom_diagnostique AS DiagnostiquesSuivi, " +
            "FROM fiche_consultation fc " +
            "JOIN diagnostique d ON fc.id_diagnostique = d.id_diagnostique " +
            "JOIN etudiant e ON fc.id_etudiant = e.id_etudiant " +
            "WHERE fc.date_prochain_rendez_vous IS NOT NULL " +
            "  AND fc.date_prochain_rendez_vous >= CURRENT_DATE " +
            "ORDER BY dateRendezVous ASC, heureRendezVous ASC", nativeQuery = true)
    List<Object[]> getProchainsRendezVous();






}
