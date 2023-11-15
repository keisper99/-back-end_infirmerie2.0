package org.institutsaintjean.INFIRMERIE_IUSJC.Components;

import lombok.RequiredArgsConstructor;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.SmsMessage;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.SmsMessageApresConnection;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.SmsApiCaller;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
public class SmsManager {

    @Autowired
    private Fiche_consultationRepository ficheConsultationRepository;

    @Autowired
    private Fiche_SuivieRepository ficheSuivieRepository;

    @Autowired
    private SmsMessageApresConnexionRepository smsMessageApresConnexionRepository;

    @Autowired
    private SmsMessageRepository smsMessageRepository;



    private SmsApiCaller smsApiCaller = new SmsApiCaller();

    public void addToSmsEnvoieDirect(SmsMessage smsMessage) {
        smsMessageRepository.save(smsMessage);
    }


    public void addToSmsMessageEnvoieApresConnexion(SmsMessageApresConnection smsMessageApresConnection) {
        smsMessageApresConnexionRepository.save(smsMessageApresConnection);
    }





    //@Scheduled(cron = "0 30 08 * * *")
    @Scheduled(fixedRate = 60000)
    public void checkNetworkAndSendSms() throws Exception {

        if (isConnectedToNetwork()) {
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Ajoute 1 jour

            Date dateUnJourAvant = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(dateUnJourAvant);

            System.out.println("belom dans shedulate apres le test de connection sms");
            List<SmsMessage> SmsMessages = smsMessageRepository.findAll();
            for (SmsMessage smsMessage : SmsMessages) {
                if(dateFormat.format(smsMessage.getDateProchainRendezVous()).equals(formattedDate)){
                    if (isConnectedToNetwork()){
                        SmsApiCaller.sendSms(Long.toString(smsMessage.getNumero()),smsMessage.getMessage());
                        smsMessageRepository.delete(smsMessage);
                    }else {
                       addToSmsMessageEnvoieApresConnexion(new SmsMessageApresConnection(smsMessage));
                    }
                }


            }
        }

    }


   // @Scheduled(cron = "0 30 08 * * *")
   @Scheduled(fixedRate = 60000)
    public void checkNetworkAndSendSmsApresConnexion() throws Exception {
        if (isConnectedToNetwork()){
            System.out.println("belom dans shedulate apres le test de connection sms apres connexion");
            List<SmsMessageApresConnection> SmsMessages = smsMessageApresConnexionRepository.findAll();
            for (SmsMessageApresConnection smsMessage : SmsMessages) {
                if (isConnectedToNetwork()){
                    SmsApiCaller.sendSms(Long.toString(smsMessage.getNumero()),smsMessage.getMessage());
                    smsMessageApresConnexionRepository.delete(smsMessage);
                }

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

   /* public void envoyerNotificationsRendezVous() throws Exception {
        SmsMessage smsMessage = new SmsMessage();
        while (true) {
            List<Fiche_Consultation> fichesConsultation = ficheConsultationRepository.findAll();
            List<Fiche_Suivie> fichesSuivie = ficheSuivieRepository.findAll();

            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Ajoute 1 jour

            Date dateUnJourAvant = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(dateUnJourAvant);


            for (Fiche_Consultation ficheConsultation : fichesConsultation) {
                if (ficheConsultation.getDateProchainRendezVous() != null &&
                        dateFormat.format(ficheConsultation.getDateProchainRendezVous()).equals(formattedDate)) {
                    String message = "Cher Etudiant,\n\n";
                    message += "\"Rappel de rendez-vous à l'infirmerie" +
                            " Vous avez un rendez-vous à l'infirmerie demain a "+ ficheConsultation.getHeureProchainRendezVous()+ ". :\n\n";

                    message += "\n\nCordialement,\nVotre clinique";

                    smsMessage.setNumero(ficheConsultation.getEtudiant().getNumeroDeTelephone());
                    smsMessage.setMessage(message);



                    if (isConnectedToNetwork()) {
                        SmsApiCaller.sendSms(Long.toString(ficheConsultation.getEtudiant().getNumeroDeTelephone()),message);

                    }else {
                        //addToQueue(smsMessage);

                    }
                }
            }

            for (Fiche_Suivie ficheSuivie : fichesSuivie) {
                if (ficheSuivie.getDateProchainRendezVous() != null &&
                        ficheSuivie.getDateProchainRendezVous().equals(dateUnJourAvant)) {
                    String message = "Cher Etudiant,\n\n";
                    message += "\"Rappel de rendez-vous à l'infirmerie" +
                            " Vous avez un rendez-vous à l'infirmerie demain a "+ ficheSuivie.getHeureProchainRendezVous()+ ". :\n\n";

                    message += "\n\nCordialement,\nVotre clinique";

                    if (isConnectedToNetwork()) {

                    }else {

                    }
                }
            }

        }
    }
*/



}
