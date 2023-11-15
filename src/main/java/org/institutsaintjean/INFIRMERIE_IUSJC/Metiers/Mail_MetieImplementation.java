package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;


import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.*;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.text.SimpleDateFormat;

@Service
@Transactional
@RequiredArgsConstructor
public class Mail_MetieImplementation implements Mail_Metier{


    private final JavaMailSender mailSender;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

/*
    @Autowired
    public Mail_MetieImplementation(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
*/

    public void sendEmailToParentPieceJointe(String emailContactUrgence, Fiche_Consultation ficheConsultation, File pieceJointe) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailContactUrgence);
        helper.setSubject("Rapport de consultation pour " + ficheConsultation.getEtudiant().getNom());

        // Construisez le contenu de l'e-mail avec les informations de la consultation
        String emailContent = "Cher parent,\n\n";
        emailContent += "Votre enfant " + ficheConsultation.getEtudiant().getNom() + " a eu une consultation le "
                + dateFormat.format(ficheConsultation.getDateCreationFC())  + ". Voici les détails de la consultation :\n\n";

// Ajout de la liste des symptômes
        if (ficheConsultation.getSymptomeList() != null && !ficheConsultation.getSymptomeList().isEmpty()) {
            emailContent += "Symptômes :\n";
            for (Symptome symptome : ficheConsultation.getSymptomeList()) {
                emailContent += "- " + symptome.getNomSymptome() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des diagnostics
        if (ficheConsultation.getDiagnostiqueList() != null && !ficheConsultation.getDiagnostiqueList().isEmpty()) {
            emailContent += "Diagnostics :\n";
            for (Diagnostique diagnostique : ficheConsultation.getDiagnostiqueList()) {
                emailContent += "- " + diagnostique.getNomDiagnostique() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des médicaments
        if (ficheConsultation.getMedicamentListConsultation() != null && !ficheConsultation.getMedicamentListConsultation().isEmpty()) {
            emailContent += "Médicaments prescrits :\n";
            for (MedicamentPrescrit medicamentPrescrit : ficheConsultation.getMedicamentListConsultation()) {
                emailContent += "- " + medicamentPrescrit.getMedicament().getNomMedicament() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des examens
        if (ficheConsultation.getExamenList() != null && !ficheConsultation.getExamenList().isEmpty()) {
            emailContent += "Examens recommandés :\n";
            for (Examen examen : ficheConsultation.getExamenList()) {
                emailContent += "- " + examen.getNomExamen() + "\n";
            }
            emailContent += "\n";
        }



        emailContent += "\n\nCordialement,\nVotre clinique";

        helper.setText(emailContent);
        helper.addAttachment("Facture.pdf", pieceJointe);

        // Envoyez l'e-mail
        mailSender.send(message);
    }

    @Override
    public void sendEmailToParent(String emailContactUrgence, Fiche_Consultation ficheConsultation) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailContactUrgence);
        helper.setSubject("Rapport de consultation de l'etudiant " + ficheConsultation.getEtudiant().getNom());

        // Construisez le contenu de l'e-mail avec les informations de la consultation
        String emailContent = "Cher parent,\n\n";
        emailContent += "Votre enfant " + ficheConsultation.getEtudiant().getNom() + " a eu une consultation le "
                + dateFormat.format(ficheConsultation.getDateCreationFC()) + ". Voici les détails de la consultation :\n\n";

// Ajout de la liste des symptômes
        if (ficheConsultation.getSymptomeList() != null && !ficheConsultation.getSymptomeList().isEmpty()) {
            emailContent += "Symptômes :\n";
            for (Symptome symptome : ficheConsultation.getSymptomeList()) {
                emailContent += "- " + symptome.getNomSymptome() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des diagnostics
        if (ficheConsultation.getDiagnostiqueList() != null && !ficheConsultation.getDiagnostiqueList().isEmpty()) {
            emailContent += "Diagnostics :\n";
            for (Diagnostique diagnostique : ficheConsultation.getDiagnostiqueList()) {
                emailContent += "- " + diagnostique.getNomDiagnostique() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des médicaments
        if (ficheConsultation.getMedicamentListConsultation() != null && !ficheConsultation.getMedicamentListConsultation().isEmpty()) {
            emailContent += "Médicaments prescrits :\n";
            for (MedicamentPrescrit medicamentPrescrit : ficheConsultation.getMedicamentListConsultation()) {
                emailContent += "- " + medicamentPrescrit.getMedicament().getNomMedicament() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des examens
        if (ficheConsultation.getExamenList() != null && !ficheConsultation.getExamenList().isEmpty()) {
            emailContent += "Examens recommandés :\n";
            for (Examen examen : ficheConsultation.getExamenList()) {
                emailContent += "- " + examen.getNomExamen() + "\n";
            }
            emailContent += "\n";
        }


        emailContent += "\n\nCordialement,\nVotre clinique";

        helper.setText(emailContent);


        // Envoyez l'e-mail
        mailSender.send(message);

    }

    @Override
    public void sendEmailToStudent(String emailEtudiant, Fiche_Consultation ficheConsultation) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailEtudiant);
        helper.setSubject("Rapport de consultation de l'etudiant " + ficheConsultation.getEtudiant().getNom());

        // Construisez le contenu de l'e-mail avec les informations de la consultation
        String emailContent = "Cher Etudiant,\n\n";
        emailContent += "Vous avez " + ficheConsultation.getEtudiant().getNom() + "effectuer une consultation le"
                + dateFormat.format(ficheConsultation.getDateCreationFC()) + ". Voici les détails de la consultation :\n\n";

// Ajout de la liste des symptômes
        if (ficheConsultation.getSymptomeList() != null && !ficheConsultation.getSymptomeList().isEmpty()) {
            emailContent += "Symptômes :\n";
            for (Symptome symptome : ficheConsultation.getSymptomeList()) {
                emailContent += "- " + symptome.getNomSymptome() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des diagnostics
        if (ficheConsultation.getDiagnostiqueList() != null && !ficheConsultation.getDiagnostiqueList().isEmpty()) {
            emailContent += "Diagnostics :\n";
            for (Diagnostique diagnostique : ficheConsultation.getDiagnostiqueList()) {
                emailContent += "- " + diagnostique.getNomDiagnostique() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des médicaments
        if (ficheConsultation.getMedicamentListConsultation() != null && !ficheConsultation.getMedicamentListConsultation().isEmpty()) {
            emailContent += "Médicaments prescrits :\n";
            for (MedicamentPrescrit medicamentPrescrit : ficheConsultation.getMedicamentListConsultation()) {
                emailContent += "- " + medicamentPrescrit.getMedicament().getNomMedicament() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des examens
        if (ficheConsultation.getExamenList() != null && !ficheConsultation.getExamenList().isEmpty()) {
            emailContent += "Examens recommandés :\n";
            for (Examen examen : ficheConsultation.getExamenList()) {
                emailContent += "- " + examen.getNomExamen() + "\n";
            }
            emailContent += "\n";
        }


        emailContent += "\n\nCordialement,\nVotre clinique";

        helper.setText(emailContent);


        // Envoyez l'e-mail
        mailSender.send(message);


    }

    @Override
    public void sendEmailToStudentPieceJointe(String emailEtudiant, Fiche_Consultation ficheConsultation, File pieceJointe) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailEtudiant);
        helper.setSubject("Rapport de consultation pour " + ficheConsultation.getEtudiant().getNom());

        // Construisez le contenu de l'e-mail avec les informations de la consultation
        String emailContent = "Cher Etudiant,\n\n";
        emailContent += "Vous avez " + ficheConsultation.getEtudiant().getNom() + "effectuer une consultation le"
                + dateFormat.format(ficheConsultation.getDateCreationFC()) + ". Voici les détails de la consultation :\n\n";

// Ajout de la liste des symptômes
        if (ficheConsultation.getSymptomeList() != null && !ficheConsultation.getSymptomeList().isEmpty()) {
            emailContent += "Symptômes :\n";
            for (Symptome symptome : ficheConsultation.getSymptomeList()) {
                emailContent += "- " + symptome.getNomSymptome() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des diagnostics
        if (ficheConsultation.getDiagnostiqueList() != null && !ficheConsultation.getDiagnostiqueList().isEmpty()) {
            emailContent += "Diagnostics :\n";
            for (Diagnostique diagnostique : ficheConsultation.getDiagnostiqueList()) {
                emailContent += "- " + diagnostique.getNomDiagnostique() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des médicaments
        if (ficheConsultation.getMedicamentListConsultation() != null && !ficheConsultation.getMedicamentListConsultation().isEmpty()) {
            emailContent += "Médicaments prescrits :\n";
            for (MedicamentPrescrit medicamentPrescrit : ficheConsultation.getMedicamentListConsultation()) {
                emailContent += "- " + medicamentPrescrit.getMedicament().getNomMedicament() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des examens
        if (ficheConsultation.getExamenList() != null && !ficheConsultation.getExamenList().isEmpty()) {
            emailContent += "Examens recommandés :\n";
            for (Examen examen : ficheConsultation.getExamenList()) {
                emailContent += "- " + examen.getNomExamen() + "\n";
            }
            emailContent += "\n";
        }



        emailContent += "\n\nCordialement,\nVotre clinique";

        helper.setText(emailContent);
        helper.addAttachment("Facture.pdf", pieceJointe);

        // Envoyez l'e-mail
        mailSender.send(message);
    }

   /* @Override
    public void sendEmailNotificationToStudent(String emailEtudiant, Fiche_Consultation ficheConsultation) throws MessagingException {
        emailEtudiant = "belomjordan@gmail.com";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailEtudiant);
        helper.setSubject("Rappel de rendez-vous à l'infirmerie de l'etudiant  " + ficheConsultation.getEtudiant().getNom());

        // Construisez le contenu de l'e-mail avec les informations de la consultation
        String emailContent = "Cher Etudiant,\n\n";
        emailContent += "\"Rappel de rendez-vous à l'infirmerie" +
                " Vous avez un rendez-vous à l'infirmerie demain a "+ ficheConsultation.getHeureProchainRendezVous()+ ". :\n\n";

        emailContent += "\n\nCordialement,\nVotre clinique";

        helper.setText(emailContent);


        // Envoyez l'e-mail
        mailSender.send(message);


    }
*/
   /* @Override
    public void sendEmailNotificationToStudentSuivie(String emailEtudiant, Fiche_Suivie ficheSuivie) throws MessagingException {
        emailEtudiant = "belomjordan@gmail.com";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailEtudiant);
        helper.setSubject("Rappel de rendez-vous à l'infirmerie de l'etudiant  " + ficheSuivie.getFiche_consultation().getEtudiant().getNom());

        // Construisez le contenu de l'e-mail avec les informations de la consultation
        String emailContent = "Cher Etudiant,\n\n";
        emailContent += "\"Rappel de rendez-vous à l'infirmerie" +
                " Vous avez un rendez-vous à l'infirmerie demain a "+ ficheSuivie.getHeureProchainRendezVous()+ ". :\n\n";

        emailContent += "\n\nCordialement,\nVotre clinique";

        helper.setText(emailContent);


        // Envoyez l'e-mail
        mailSender.send(message);


    }*/

    public void sendEmailToParentPieceJointeSuivie(String emailContactUrgence, Fiche_Suivie ficheSuivie, File pieceJointe) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailContactUrgence);
        helper.setSubject("Rapport de consultation pour " + ficheSuivie.getFiche_consultation().getEtudiant().getNom());

        // Construisez le contenu de l'e-mail avec les informations de la consultation
        String emailContent = "Cher parent,\n\n";
        emailContent += "Votre enfant " + ficheSuivie.getFiche_consultation().getEtudiant().getNom() + " a eu une consultation le "
                + dateFormat.format(ficheSuivie.getDateRendezVous())  + ". Voici les détails de la consultation :\n\n";

// Ajout de la liste des symptômes
        if (ficheSuivie.getSymptomeList() != null && !ficheSuivie.getSymptomeList().isEmpty()) {
            emailContent += "Symptômes :\n";
            for (Symptome symptome : ficheSuivie.getSymptomeList()) {
                emailContent += "- " + symptome.getNomSymptome() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des diagnostics
        if (ficheSuivie.getDiagnostiqueList() != null && !ficheSuivie.getDiagnostiqueList().isEmpty()) {
            emailContent += "Diagnostics :\n";
            for (Diagnostique diagnostique : ficheSuivie.getDiagnostiqueList()) {
                emailContent += "- " + diagnostique.getNomDiagnostique() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des médicaments
        if (ficheSuivie.getMedicamentListSuivie() != null && !ficheSuivie.getMedicamentListSuivie().isEmpty()) {
            emailContent += "Médicaments prescrits :\n";
            for (MedicamentPrescrit medicamentPrescrit : ficheSuivie.getMedicamentListSuivie()) {
                emailContent += "- " + medicamentPrescrit.getMedicament().getNomMedicament() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des examens
        if (ficheSuivie.getExamenList() != null && !ficheSuivie.getExamenList().isEmpty()) {
            emailContent += "Examens recommandés :\n";
            for (Examen examen : ficheSuivie.getExamenList()) {
                emailContent += "- " + examen.getNomExamen() + "\n";
            }
            emailContent += "\n";
        }



        emailContent += "\n\nCordialement,\nVotre clinique";

        helper.setText(emailContent);
        helper.addAttachment("Facture.pdf", pieceJointe);

        // Envoyez l'e-mail
        mailSender.send(message);
    }

    @Override
    public void sendEmailToParentSuivie(String emailContactUrgence, Fiche_Suivie ficheSuivie) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailContactUrgence);
        helper.setSubject("Rapport de consultation de l'etudiant " + ficheSuivie.getFiche_consultation().getEtudiant().getNom());

        // Construisez le contenu de l'e-mail avec les informations de la consultation
        String emailContent = "Cher parent,\n\n";
        emailContent += "Votre enfant " + ficheSuivie.getFiche_consultation().getEtudiant().getNom() + " a eu une consultation le "
                + dateFormat.format(ficheSuivie.getDateRendezVous()) + ". Voici les détails de la consultation :\n\n";

// Ajout de la liste des symptômes
        if (ficheSuivie.getSymptomeList() != null && !ficheSuivie.getSymptomeList().isEmpty()) {
            emailContent += "Symptômes :\n";
            for (Symptome symptome : ficheSuivie.getSymptomeList()) {
                emailContent += "- " + symptome.getNomSymptome() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des diagnostics
        if (ficheSuivie.getDiagnostiqueList() != null && !ficheSuivie.getDiagnostiqueList().isEmpty()) {
            emailContent += "Diagnostics :\n";
            for (Diagnostique diagnostique : ficheSuivie.getDiagnostiqueList()) {
                emailContent += "- " + diagnostique.getNomDiagnostique() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des médicaments
        if (ficheSuivie.getMedicamentListSuivie() != null && !ficheSuivie.getMedicamentListSuivie().isEmpty()) {
            emailContent += "Médicaments prescrits :\n";
            for (MedicamentPrescrit medicamentPrescrit : ficheSuivie.getMedicamentListSuivie()) {
                emailContent += "- " + medicamentPrescrit.getMedicament().getNomMedicament() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des examens
        if (ficheSuivie.getExamenList() != null && !ficheSuivie.getExamenList().isEmpty()) {
            emailContent += "Examens recommandés :\n";
            for (Examen examen : ficheSuivie.getExamenList()) {
                emailContent += "- " + examen.getNomExamen() + "\n";
            }
            emailContent += "\n";
        }


        emailContent += "\n\nCordialement,\nVotre clinique";

        helper.setText(emailContent);


        // Envoyez l'e-mail
        mailSender.send(message);

    }

    @Override
    public void sendEmailToStudentSuivie(String emailEtudiant, Fiche_Suivie ficheSuivie) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailEtudiant);
        helper.setSubject("Rapport de consultation de l'etudiant " + ficheSuivie.getFiche_consultation().getEtudiant().getNom());

        // Construisez le contenu de l'e-mail avec les informations de la consultation
        String emailContent = "Cher Etudiant,\n\n";
        emailContent += "Vous avez " + ficheSuivie.getFiche_consultation().getEtudiant().getNom() + "effectuer une consultation le"
                + dateFormat.format(ficheSuivie.getDateRendezVous()) + ". Voici les détails de la consultation :\n\n";

// Ajout de la liste des symptômes
        if (ficheSuivie.getSymptomeList() != null && !ficheSuivie.getSymptomeList().isEmpty()) {
            emailContent += "Symptômes :\n";
            for (Symptome symptome : ficheSuivie.getSymptomeList()) {
                emailContent += "- " + symptome.getNomSymptome() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des diagnostics
        if (ficheSuivie.getDiagnostiqueList() != null && !ficheSuivie.getDiagnostiqueList().isEmpty()) {
            emailContent += "Diagnostics :\n";
            for (Diagnostique diagnostique : ficheSuivie.getDiagnostiqueList()) {
                emailContent += "- " + diagnostique.getNomDiagnostique() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des médicaments
        if (ficheSuivie.getMedicamentListSuivie() != null && !ficheSuivie.getMedicamentListSuivie().isEmpty()) {
            emailContent += "Médicaments prescrits :\n";
            for (MedicamentPrescrit medicamentPrescrit : ficheSuivie.getMedicamentListSuivie()) {
                emailContent += "- " + medicamentPrescrit.getMedicament().getNomMedicament() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des examens
        if (ficheSuivie.getExamenList() != null && !ficheSuivie.getExamenList().isEmpty()) {
            emailContent += "Examens recommandés :\n";
            for (Examen examen : ficheSuivie.getExamenList()) {
                emailContent += "- " + examen.getNomExamen() + "\n";
            }
            emailContent += "\n";
        }


        emailContent += "\n\nCordialement,\nVotre clinique";

        helper.setText(emailContent);


        // Envoyez l'e-mail
        mailSender.send(message);


    }

    @Override
    public void sendEmailToStudentPieceJointeSuivie(String emailEtudiant, Fiche_Suivie ficheSuivie, File pieceJointe) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailEtudiant);
        helper.setSubject("Rapport de consultation pour " + ficheSuivie.getFiche_consultation().getEtudiant().getNom());

        // Construisez le contenu de l'e-mail avec les informations de la consultation
        String emailContent = "Cher Etudiant,\n\n";
        emailContent += "Vous avez " + ficheSuivie.getFiche_consultation().getEtudiant().getNom() + "effectuer une consultation le"
                + dateFormat.format(ficheSuivie.getDateRendezVous()) + ". Voici les détails de la consultation :\n\n";

// Ajout de la liste des symptômes
        if (ficheSuivie.getSymptomeList() != null && !ficheSuivie.getSymptomeList().isEmpty()) {
            emailContent += "Symptômes :\n";
            for (Symptome symptome : ficheSuivie.getSymptomeList()) {
                emailContent += "- " + symptome.getNomSymptome() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des diagnostics
        if (ficheSuivie.getDiagnostiqueList() != null && !ficheSuivie.getDiagnostiqueList().isEmpty()) {
            emailContent += "Diagnostics :\n";
            for (Diagnostique diagnostique : ficheSuivie.getDiagnostiqueList()) {
                emailContent += "- " + diagnostique.getNomDiagnostique() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des médicaments
        if (ficheSuivie.getMedicamentListSuivie() != null && !ficheSuivie.getMedicamentListSuivie().isEmpty()) {
            emailContent += "Médicaments prescrits :\n";
            for (MedicamentPrescrit medicamentPrescrit : ficheSuivie.getMedicamentListSuivie()) {
                emailContent += "- " + medicamentPrescrit.getMedicament().getNomMedicament() + "\n";
            }
            emailContent += "\n";
        }

// Ajout de la liste des examens
        if (ficheSuivie.getExamenList() != null && !ficheSuivie.getExamenList().isEmpty()) {
            emailContent += "Examens recommandés :\n";
            for (Examen examen : ficheSuivie.getExamenList()) {
                emailContent += "- " + examen.getNomExamen() + "\n";
            }
            emailContent += "\n";
        }



        emailContent += "\n\nCordialement,\nVotre clinique";

        helper.setText(emailContent);
        helper.addAttachment("Facture.pdf", pieceJointe);

        // Envoyez l'e-mail
        mailSender.send(message);
    }



}
