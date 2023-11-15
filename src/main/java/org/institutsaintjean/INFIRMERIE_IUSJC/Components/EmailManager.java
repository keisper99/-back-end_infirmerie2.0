package org.institutsaintjean.INFIRMERIE_IUSJC.Components;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.*;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Mail_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.SmsApiCaller;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.EmailMessagePieceJointeRepository;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.EmailRepository;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.InfirmiereRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
@RequiredArgsConstructor
public class EmailManager {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailMessagePieceJointeRepository emailMessagePieceJointeRepository;

    //private final Queue<EmailMessage> emailQueue = new LinkedList<>();
    private final JavaMailSender mailSender;

    @Autowired
    private Mail_Metier mailMetier;

    public void addToEmailMessage(EmailMessage emailMessage) {
        emailRepository.save(emailMessage);
        //emailQueue.offer(emailMessage);
    }

    public void addToEmailMessagePieceJointe(EmailMessagePieceJointe emailMessagePieceJointe) {
        emailMessagePieceJointeRepository.save(emailMessagePieceJointe);
        //emailQueue.offer(emailMessage);
    }

    @Scheduled(fixedRate = 60000)
    public void checkNetworkAndSendEmails() throws MessagingException {
        if (isConnectedToNetwork()) {
            System.out.println("belom dans shedulate apres le test de connection df");
            List<EmailMessage> emails = emailRepository.findAll();
           /* while (!emailQueue.isEmpty()) {
                EmailMessage emailMessage = emailQueue.poll();
                sendEmailToStudentHorsConnexion(emailMessage.getEmailEtudiant(),emailMessage);
                sendEmailToParentHorsConnexion(emailMessage.getEmailContactUrgence(),emailMessage);
            }*/
            for (EmailMessage emailMessage : emails) {
                sendEmailToStudentHorsConnexion(emailMessage.getEmailEtudiant(), emailMessage);
                sendEmailToParentHorsConnexion(emailMessage.getEmailContactUrgence(), emailMessage);
                emailRepository.delete(emailMessage);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void checkNetworkAndSendEmailsPienceJointe() throws MessagingException {
        System.out.println("belom dans shedulate");
        List<EmailMessagePieceJointe> emailsPieceJointe = emailMessagePieceJointeRepository.findAll();
        if (isConnectedToNetwork()) {
            System.out.println("belom dans shedulate apres le test de connection");
           /* while (!emailQueue.isEmpty()) {
                EmailMessage emailMessage = emailQueue.poll();
                sendEmailToStudentPieceJointeHorsConnexion(emailMessage.getEmailEtudiant(),emailMessage,emailMessage.getPieceJointe());
                sendEmailToParentPieceJointeHorsConnexion(emailMessage.getEmailContactUrgence(),emailMessage,emailMessage.getPieceJointe());
            }*/
            for (EmailMessagePieceJointe emailMessagePieceJointe : emailsPieceJointe) {
                sendEmailToStudentPieceJointeHorsConnexion(emailMessagePieceJointe.getEmailEtudiant(), emailMessagePieceJointe,emailMessagePieceJointe.getPieceJointe());
                sendEmailToParentPieceJointeHorsConnexion(emailMessagePieceJointe.getEmailContactUrgence(),emailMessagePieceJointe ,emailMessagePieceJointe.getPieceJointe());
                emailMessagePieceJointeRepository.delete(emailMessagePieceJointe);
            }
        }
    }

    public boolean isConnectedToNetwork() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return address.isReachable(5000); // 5000 ms timeout
        } catch (UnknownHostException e) {
            return false; // Unable to resolve host, so not connected
        } catch (Exception e) {
            return false; // Other exceptions, assume not connected
        }
    }




    public void sendEmailToParentPieceJointeHorsConnexion(String emailContactUrgence, EmailMessagePieceJointe emailMessagePieceJointe, File pieceJointe) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailContactUrgence);
        helper.setSubject(emailMessagePieceJointe.getSubject());
        helper.setText(emailMessagePieceJointe.getContent());
        helper.addAttachment("Facture.pdf", pieceJointe);

        // Envoyez l'e-mail
        mailSender.send(message);
    }


    public void sendEmailToParentHorsConnexion(String emailContactUrgence, EmailMessage emailMessage) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailContactUrgence);
        helper.setSubject(emailMessage.getSubject());
        helper.setText(emailMessage.getContent());


        // Envoyez l'e-mail
        mailSender.send(message);

    }


    public void sendEmailToStudentHorsConnexion(String emailEtudiant, EmailMessage emailMessage) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailEtudiant);
        helper.setSubject(emailMessage.getSubject());
        helper.setText(emailMessage.getContent());


        // Envoyez l'e-mail
        mailSender.send(message);


    }


    public void sendEmailToStudentPieceJointeHorsConnexion(String emailEtudiant, EmailMessagePieceJointe emailMessagePieceJointe, File pieceJointe) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurez l'expéditeur, le destinataire, l'objet et le contenu de l'e-mail
        helper.setFrom("isj.infirmerie@institutsaintjean.org");
        helper.setTo(emailEtudiant);
        helper.setSubject(emailMessagePieceJointe.getSubject());
        helper.setText(emailMessagePieceJointe.getContent());
        helper.addAttachment("Facture.pdf", pieceJointe);

        // Envoyez l'e-mail
        mailSender.send(message);
    }






}
