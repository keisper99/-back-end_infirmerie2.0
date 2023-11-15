package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;
import jakarta.mail.MessagingException;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.EmailMessage;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Consultation;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Suivie;


import java.io.File;

public interface Mail_Metier {
    public void sendEmailToParentPieceJointe(String emailContactUrgence, Fiche_Consultation ficheConsultation, File pieceJointe) throws MessagingException;
    public void sendEmailToParent(String emailContactUrgence, Fiche_Consultation ficheConsultation) throws MessagingException;

    public void sendEmailToStudent(String emailEtudiant, Fiche_Consultation ficheConsultation) throws MessagingException;

    public void sendEmailToStudentPieceJointe(String emailEtudiant, Fiche_Consultation ficheConsultation, File pieceJointe) throws MessagingException;

   /* public void sendEmailNotificationToStudent(String emailEtudiant, Fiche_Consultation ficheConsultation) throws MessagingException;
    public void sendEmailNotificationToStudentSuivie(String emailEtudiant, Fiche_Suivie ficheSuivie) throws MessagingException;
*/
    public void sendEmailToParentPieceJointeSuivie(String emailContactUrgence, Fiche_Suivie ficheSuivie, File pieceJointe) throws MessagingException;
    public void sendEmailToParentSuivie(String emailContactUrgence, Fiche_Suivie ficheSuivie) throws MessagingException;

    public void sendEmailToStudentSuivie(String emailEtudiant, Fiche_Suivie ficheSuivie) throws MessagingException;

    public void sendEmailToStudentPieceJointeSuivie(String emailEtudiant, Fiche_Suivie ficheSuivie, File pieceJointe) throws MessagingException;



}
