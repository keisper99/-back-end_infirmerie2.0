/*
package org.institutsaintjean.INFIRMERIE_IUSJC.Configurations.NotifAuto;

import jakarta.mail.MessagingException;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Consultation;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Suivie;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Mail_Metier;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.Fiche_SuivieRepository;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.Fiche_consultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class ScheduledTask implements Runnable{


    Fiche_consultationRepository ficheConsultationRepository;

     Fiche_SuivieRepository ficheSuivieRepository;
      Mail_Metier mailMetier;


    @Override
    //@Scheduled(cron = "0 45 20 * * ?") // À 18h chaque jour
    public void run() {

                System.out.println("Scheduled task executed jordane");


                List<Fiche_Consultation> fichesConsultation = ficheConsultationRepository.findAll();
        System.out.println("belom jordan ddfcgbjhnkmlkjhgbjhnkl jordan");

                List<Fiche_Suivie> fichesSuivie = ficheSuivieRepository.findAll();


                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_MONTH, 1); // Ajoute 1 jour

                Date dateUnJourAvant = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(dateUnJourAvant);

                System.out.println("belom jordan ddfcgbjhnkmlkjhgbjhnkl jordan");

                for (Fiche_Consultation ficheConsultation : fichesConsultation) {
                    System.out.println(formattedDate);
                    System.out.println(ficheConsultation.getDateProchainRendezVous());
                    if (ficheConsultation.getDateProchainRendezVous() != null &&
                            dateFormat.format(ficheConsultation.getDateProchainRendezVous()).equals(formattedDate)) {
                        System.out.println("belom jordan ddfcgbjhnkmlkjhgbjhnkl;");
                        try {
                            mailMetier.sendEmailNotificationToStudent(ficheConsultation.getEtudiant().getEmailEtudiant(), ficheConsultation);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                for (Fiche_Suivie ficheSuivie : fichesSuivie) {
                    if (ficheSuivie.getDateProchainRendezVous() != null &&
                            ficheSuivie.getDateProchainRendezVous().equals(dateUnJourAvant)) {
                        try {
                            mailMetier.sendEmailNotificationToStudentSuivie(ficheSuivie.getFiche_consultation().getEtudiant().getEmailEtudiant(),ficheSuivie);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }


            }



}
*/
