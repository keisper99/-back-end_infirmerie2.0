

package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;

import org.institutsaintjean.INFIRMERIE_IUSJC.Components.EmailManager;
import org.institutsaintjean.INFIRMERIE_IUSJC.Components.SmsManager;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.CredentialsDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.InfirmiereDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.SignUpDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.*;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.*;
import org.institutsaintjean.INFIRMERIE_IUSJC.exceptions.AppException;
import org.institutsaintjean.INFIRMERIE_IUSJC.mappers.InfirmiereMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



@Service
@Transactional
@RequiredArgsConstructor
public class Infirmiere_Metier_Implementation implements Infirmiere_Metier {









    @Autowired
    private InfirmiereRepository infirmiereRepository;

 @Autowired
    private MedicamentRepository medicamentRepository;
    @Autowired
    private Fiche_consultationRepository ficheConsultationRepository;
    @Autowired
    private ExamenRepository examenRepository;
    @Autowired
    private SymptomeRepository symptomeRepository;
    @Autowired
    private Fiche_SuivieRepository ficheSuivieRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private DiagnostiqueRepository diagnostiqueRepository;

    @Autowired
    private MedicamentPrescritRepository medicamentPrescritRepository;

    @Autowired
    private Mail_Metier mailMetier;

    @Autowired
    private EmailManager emailManager;

    @Autowired
    private SmsManager smsManager;

    @Autowired
    private Rapport_Metier rapportMetier;

    private final PasswordEncoder passwordEncoder;

    private final InfirmiereMapper infirmiereMapper;

    private final EmailMessage emailMessage  = new EmailMessage();;
    private final EmailMessagePieceJointe emailMessagePieceJointe  = new EmailMessagePieceJointe();;


    public Medicament ajouterMedicament(@RequestParam(required = false) Long idMedicament, @RequestBody Medicament medicament) {
        Infirmiere infirmiereExistante = infirmiereRepository.findById(medicament.getInfirmiere().getIdInfirmiere())
                .orElseThrow(() -> new IllegalArgumentException("Infirmière non trouvée"));

        List<Medicament> medicamentList = infirmiereExistante.getMedicamentListInfirmiere();
        if (medicamentList == null) {
            medicamentList = new ArrayList<>(); // Initialisation de la liste
        }

        if (idMedicament != null) {
            Optional<Medicament> medicamentExistant = medicamentRepository.findById(idMedicament);
            if (medicamentExistant.isPresent()) {
                Medicament medicamentRecherche = medicamentExistant.get();
                medicamentRecherche.setQuantiteDisponible(medicamentRecherche.getQuantiteDisponible() + medicament.getQuantiteDisponible());
                medicamentRepository.save(medicamentRecherche);
                return medicamentRecherche;
            }
        }

        medicament.setDateEnregistrement(new Date());
        medicamentList.add(medicament);
        medicament.setInfirmiere(infirmiereExistante);
        medicamentRepository.save(medicament);
        infirmiereExistante.setMedicamentListInfirmiere(medicamentList);
        infirmiereRepository.save(infirmiereExistante);

        return medicament;
    }





    @Override
   public Medicament ajouterMedicament(Long idMedicament, Infirmiere infirmiere, Medicament medicament) {
       Infirmiere infirmiereExistante = infirmiereRepository.findById(infirmiere.getIdInfirmiere())
               .orElseThrow(() -> new IllegalArgumentException("Infirmière non trouvée"));

       List<Medicament> medicamentList = infirmiereExistante.getMedicamentListInfirmiere();
       if (medicamentList == null) {
           medicamentList = new ArrayList<>();
       }

       if (idMedicament != null) {
           Optional<Medicament> medicamentExistantOptional = medicamentRepository.findById(idMedicament);
           if (medicamentExistantOptional.isPresent()) {

               Medicament medicamentExistant = medicamentExistantOptional.get();
               int quantiteActuelle = medicamentExistant.getQuantiteDisponible();
               int quantiteAjoutee = medicament.getQuantiteDisponible();
               int nouvelleQuantite = quantiteActuelle + quantiteAjoutee;
               medicamentExistant.setQuantiteDisponible(nouvelleQuantite);
               medicamentRepository.save(medicamentExistant);
               return medicamentExistant;
           }
       }
       medicament.setDateEnregistrement(new Date());
       medicamentList.add(medicament);
       medicament.setInfirmiere(infirmiereExistante);
       infirmiereExistante.setMedicamentListInfirmiere(medicamentList);
       medicamentRepository.save(medicament);
       infirmiereRepository.save(infirmiereExistante);

       return medicamentRepository.save(medicament);
   }




    @Override
    public InfirmiereDto login(CredentialsDto credentialsDto) {
        Infirmiere infirmiere = infirmiereRepository.findByLogin(credentialsDto.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), infirmiere.getPassword())) {
            return infirmiereMapper.toUserDto(infirmiere);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }
    @Override
    public InfirmiereDto register(SignUpDto userDto) {
        Optional<Infirmiere> optionalUser = infirmiereRepository.findByLogin(userDto.login());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        Infirmiere user = infirmiereMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));

        Infirmiere savedUser = infirmiereRepository.save(user);

        return infirmiereMapper.toUserDto(savedUser);
    }
    @Override
    public InfirmiereDto findByLogin(String login) {
        Infirmiere user = infirmiereRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return infirmiereMapper.toUserDto(user);
    }
    @Override
    public InfirmiereDto updateLoginAndPassword(Long infirmiereId, String newLogin, String newPassword) {
        // Rechercher l'infirmière par son ID
        Infirmiere infirmiere = infirmiereRepository.findById(infirmiereId)
                .orElseThrow(() -> new EntityNotFoundException("Infirmiere non trouvee avec l'ID: " + infirmiereId));

        // Vérifier si le nouveau login existe déjà pour un autre utilisateur
        Optional<Infirmiere> existingUserWithNewLogin = infirmiereRepository.findByLogin(newLogin);
        if (existingUserWithNewLogin.isPresent() && !existingUserWithNewLogin.get().getIdInfirmiere().equals(infirmiereId)) {
            throw new AppException("Login est deja utilise", HttpStatus.BAD_REQUEST);
        }

        // Mettre à jour le login et le mot de passe
        infirmiere.setLogin(newLogin);
        infirmiere.setPassword(passwordEncoder.encode(CharBuffer.wrap(newPassword)));
        Infirmiere updatedInfirmiere = infirmiereRepository.save(infirmiere);

        return infirmiereMapper.toUserDto(updatedInfirmiere);
    }



@Override
    public Fiche_Consultation creerFicheConsultation(Long idEtudiant, Long idInfirmiere, Fiche_Consultation fiche_consultation,
                                                     List<Long> idMedicament, List<Long> idSymptome, List<Long> idExamen,
                                                     List<Long> idDiagnostique,
                                                     List<Integer> quantiteMedicamentPrescrite,List<String> posologieList,
                                                     List<String> nouveauxSymptomes,
                                                     List<String> nouveauxExamens, List<String> nouveauxDiagnostique) throws JRException, IOException {


        SmsMessage smsMessage = new SmsMessage();
        Infirmiere infirmiere = infirmiereRepository.findById(idInfirmiere)
                .orElseThrow(() -> new IllegalArgumentException("Infirmiere non trouvée"));

        fiche_consultation.setInfirmiere(infirmiere);

        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant non trouvé"));

        fiche_consultation.setEtudiant(etudiant);

        fiche_consultation.setDateCreationFC(new Date());

    System.out.println("belom jordan djhdskfs "+ fiche_consultation.getHeureSortieConsultation());

        // Vérifier et initialiser les listes si elles sont null
        if (fiche_consultation.getMedicamentListConsultation() == null) {
            fiche_consultation.setMedicamentListConsultation(new HashSet<>());
        }
        if (fiche_consultation.getSymptomeList() == null) {
            fiche_consultation.setSymptomeList(new HashSet<>());
        }
        if (fiche_consultation.getExamenList() == null) {
            fiche_consultation.setExamenList(new HashSet<>());
        }
        if (fiche_consultation.getDiagnostiqueList() == null) {
            fiche_consultation.setDiagnostiqueList(new HashSet<>());
        }

        System.out.println("belom jordan heure"+ fiche_consultation.getHeureSortieConsultation());







    // Ajouter les médicaments existants à la fiche de consultation
    if (idMedicament != null && !idMedicament.isEmpty()) {
        for (int i = 0; i < idMedicament.size(); i++) {
            Long medicamentId = idMedicament.get(i);
            int quantitePrescrite = quantiteMedicamentPrescrite.get(i); // Liste de quantités prescrites, doit être fournie depuis l'interface utilisateur
            String posologie = posologieList.get(i);
            Medicament medicament = medicamentRepository.findById(medicamentId)
                    .orElseThrow(() -> new IllegalArgumentException("Médicament non trouvé"));

            // Vérifier si la quantité prescrite est valide (supérieure à 0 et disponible en stock)
            if (quantitePrescrite > 0 && medicament.getQuantiteDisponible() >= quantitePrescrite) {
                // Mettre à jour la quantité disponible
                int nouvelleQuantiteDisponible = medicament.getQuantiteDisponible() - quantitePrescrite;
                medicament.setQuantiteDisponible(nouvelleQuantiteDisponible);

                // Ajouter le médicament à la liste des médicaments prescrits
                MedicamentPrescrit medicamentPrescrit = new MedicamentPrescrit();
                medicamentPrescrit.setMedicament(medicament);
                medicamentPrescrit.setQuantitePrescrite(quantitePrescrite);
                medicamentPrescrit.setPosogie(posologie);
                medicamentPrescrit.setFicheConsultation(fiche_consultation);

                fiche_consultation.getMedicamentListConsultation().add(medicamentPrescrit);
            } else {
                // Gérer l'erreur si la quantité prescrite est invalide ou insuffisante en stock
                throw new IllegalArgumentException("Quantité prescrite invalide ou insuffisante en stock pour le médicament : " + medicament.getNomMedicament());
            }
        }
    }



        // Ajouter les symptômes existants à la fiche de consultation
        if (idSymptome != null && !idSymptome.isEmpty()) {
            List<Symptome> symptomeList = symptomeRepository.findAllById(idSymptome);
            fiche_consultation.getSymptomeList().addAll(new ArrayList<>(symptomeList));
        }

        // Ajouter les examens existants à la fiche de consultation
        if (idExamen != null && !idExamen.isEmpty()) {
            List<Examen> examenList = examenRepository.findAllById(idExamen);
            fiche_consultation.getExamenList().addAll(new ArrayList<>(examenList));
        }

        // Ajouter les Diagnostiques existants à la fiche de consultation
        if (idDiagnostique != null && !idDiagnostique.isEmpty()) {
            List<Diagnostique> diagnostiqueList = diagnostiqueRepository.findAllById(idDiagnostique);
            fiche_consultation.getDiagnostiqueList().addAll(new ArrayList<>(diagnostiqueList));
        }

        // Ajouter les nouveaux symptômes à la fiche de consultation
        if (nouveauxSymptomes != null && !nouveauxSymptomes.isEmpty()) {
            for (String nouveauSymptome : nouveauxSymptomes) {
                Symptome symptome = new Symptome();
                symptome.setNomSymptome(nouveauSymptome);
                Symptome symptomeAjoute = symptomeRepository.save(symptome);
                fiche_consultation.getSymptomeList().add(symptomeAjoute);
            }
        }

        // Ajouter les nouveaux examens à la fiche de consultation
        if (nouveauxExamens != null && !nouveauxExamens.isEmpty()) {
            for (String nouveauExamen : nouveauxExamens) {
                Examen examen = new Examen();
                examen.setNomExamen(nouveauExamen);
                Examen examenAjoute = examenRepository.save(examen);
                fiche_consultation.getExamenList().add(examenAjoute);
            }
        }

        // Ajouter les nouveaux diagnostiques à la fiche de consultation
        if (nouveauxDiagnostique != null && !nouveauxDiagnostique.isEmpty()) {
            for (String nouveauDiagnostique : nouveauxDiagnostique) {
                Diagnostique diagnostique = new Diagnostique();
                diagnostique.setNomDiagnostique(nouveauDiagnostique);
                Diagnostique diagnostiqueAjoute = diagnostiqueRepository.save(diagnostique);
                fiche_consultation.getDiagnostiqueList().add(diagnostiqueAjoute);
            }
        }




    Set<MedicamentPrescrit> medicamentList = fiche_consultation.getMedicamentListConsultation();
    if (medicamentList != null && !medicamentList.isEmpty()) {
        // Créer une nouvelle facture associée à l'étudiant et à la fiche de consultation
        Facture facture = new Facture();
        facture.setDateCreationFacture(new Date());
        facture.setEtudiant(fiche_consultation.getEtudiant());
        facture.setFicheConsultation(fiche_consultation);
        facture.setStatutFacture(false);

        // Vous pouvez ajouter d'autres propriétés de la facture ici selon vos besoins

            // Sauvegarder la nouvelle facture
            Facture factureCree = factureRepository.save(facture);

        // Maintenant, associez les médicaments prescrits à la nouvelle facture:

        int montantTotal = 0;

        List<MedicamentPrescrit> medicamentsPrescrits = new ArrayList<>();
        for (MedicamentPrescrit medicamentPrescrit : medicamentList) {
            medicamentsPrescrits.add(medicamentPrescrit);
            // Calculer le montant pour chaque médicament prescrit
            int quantitePrescrite = medicamentPrescrit.getQuantitePrescrite();
            int prixUnitaire = medicamentPrescrit.getMedicament().getPrixUnitaire();
            int montantMedicament = quantitePrescrite * prixUnitaire;

            // Ajouter le montant du médicament à la somme totale
            montantTotal += montantMedicament;
        }
        facture.setMontantFacute(montantTotal);



        // Sauvegarder les médicaments prescrits avec la référence à la nouvelle facture
        medicamentPrescritRepository.saveAll(medicamentsPrescrits);

        // Ajouter tous les médicaments prescrits à la nouvelle facture
        factureCree.setMedicamentPrescritsList(medicamentsPrescrits);
        for (MedicamentPrescrit medicamentPrescrit : medicamentsPrescrits) {
            medicamentPrescrit.setFacture(factureCree);
        }

        fiche_consultation.setFacture(factureCree);







        // Ajouter la facture à la liste des factures de l'étudiant
            List<Facture> facturesEtudiant = new ArrayList<>(fiche_consultation.getEtudiant().getFactures());
            facturesEtudiant.add(factureCree);
        fiche_consultation.getEtudiant().setFactures(facturesEtudiant);

            // Sauvegarder les modifications de l'étudiant
            etudiantRepository.save(fiche_consultation.getEtudiant());
        }
    Fiche_Consultation ficheConsultation = ficheConsultationRepository.save(fiche_consultation);


    String emailContent;
    if (medicamentList != null && !medicamentList.isEmpty()) {

        Long NumFacture = ficheConsultation.getFacture().getNumFacture();
        File pieceJointe = rapportMetier.exportReportFile(NumFacture,"pdf");
        // Construisez le contenu de l'e-mail avec les informations de la consultation
        emailContent = "Cher parent,\n\n";
        emailContent += "Votre enfant " + ficheConsultation.getEtudiant().getNom() + " a eu une consultation le "
                + ficheConsultation.getDateCreationFC() + ". Voici les détails de la consultation :\n\n";

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
        emailMessagePieceJointe.setContent(emailContent);
        emailMessagePieceJointe.setSubject("Rapport de consultation pour " + ficheConsultation.getEtudiant().getNom());
        emailMessagePieceJointe.setPieceJointe(pieceJointe);
        emailMessagePieceJointe.setEmailEtudiant(etudiant.getEmailEtudiant());
        emailMessagePieceJointe.setEmailContactUrgence(etudiant.getEmailContactUrgence());


        if (emailManager.isConnectedToNetwork()) {
            try {
            mailMetier.sendEmailToParentPieceJointe(etudiant.getEmailContactUrgence(), ficheConsultation,pieceJointe);
            mailMetier.sendEmailToStudentPieceJointe(etudiant.getEmailEtudiant(),ficheConsultation,pieceJointe);

            } catch (MessagingException e) {
                // Gérer les erreurs liées à l'envoi d'e-mails
                e.printStackTrace();
            }

        } else {
            emailManager.addToEmailMessagePieceJointe(emailMessagePieceJointe);
        }


    } else {
        // Construisez le contenu de l'e-mail avec les informations de la consultation
        emailContent = "Cher parent,\n\n";
        emailContent += "Votre enfant " + ficheConsultation.getEtudiant().getNom() + " a eu une consultation le "
                + ficheConsultation.getDateCreationFC() + ". Voici les détails de la consultation :\n\n";

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

// Ajout de la liste des examens
        if (ficheConsultation.getExamenList() != null && !ficheConsultation.getExamenList().isEmpty()) {
            emailContent += "Examens recommandés :\n";
            for (Examen examen : ficheConsultation.getExamenList()) {
                emailContent += "- " + examen.getNomExamen() + "\n";
            }
            emailContent += "\n";
        }



        emailContent += "\n\nCordialement,\nVotre clinique";
        emailMessage.setContent(emailContent);
        emailMessage.setSubject("Rapport de consultation pour " + ficheConsultation.getEtudiant().getNom());
        emailMessage.setEmailEtudiant(etudiant.getEmailEtudiant());
        emailMessage.setEmailContactUrgence(etudiant.getEmailContactUrgence());


        if (emailManager.isConnectedToNetwork()) {
            try {
            mailMetier.sendEmailToParent(etudiant.getEmailContactUrgence(), ficheConsultation);
            mailMetier.sendEmailToStudent(etudiant.getEmailEtudiant(), ficheConsultation);

            } catch (MessagingException e) {
                // Gérer les erreurs liées à l'envoi d'e-mails
                e.printStackTrace();
            }

        } else {
            emailManager.addToEmailMessage(emailMessage);
        }


    }


    if (ficheConsultation.getDateProchainRendezVous()!= null){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String message = "Cher "+fiche_consultation.getEtudiant().getNom()+",\n\n";
        message += "\"Rappel de rendez-vous à l'infirmerie \n\n" +
                " Vous avez un rendez-vous à l'infirmerie demain a "+ timeFormat.format(ficheConsultation.getHeureProchainRendezVous()) + ".\n\n";

        message += "\n\nCordialement,\nVotre clinique";
        smsMessage.setNumero(ficheConsultation.getEtudiant().getNumeroDeTelephone());
        smsMessage.setMessage(message);
        smsMessage.setDateProchainRendezVous(ficheConsultation.getDateProchainRendezVous());
        smsManager.addToSmsEnvoieDirect(smsMessage);

    }





    return ficheConsultation;
    }

    @Override
    public Fiche_Suivie creerFicheSuivie(Long idFicheConsultation,
                                         Long idInfirmiere,
                                         Fiche_Suivie fiche_suivie,
                                         List<Long> idMedicament,
                                         List<Long> idSymptome,
                                         List<Long> idExamen,
                                         List<Long> idDiagnostique,
                                         List<Integer> quantiteMedicamentPrescrite,
                                         List<String> posologieList,
                                         List<String> nouveauxSymptomes,
                                         List<String> nouveauxExamens,
                                         List<String> nouveauxDiagnostique) throws JRException, IOException {
        SmsMessage smsMessage = new SmsMessage();

        Infirmiere infirmiere = infirmiereRepository.findById(idInfirmiere)
                .orElseThrow(() -> new IllegalArgumentException("Infirmiere non trouvée"));

        fiche_suivie.setInfirmiere(infirmiere);

        Fiche_Consultation ficheConsultation = ficheConsultationRepository.findById(idFicheConsultation)//
                .orElseThrow(() -> new IllegalArgumentException("Fiche de consultation non trouvé"));

        fiche_suivie.setFiche_consultation(ficheConsultation);

        Etudiant etudiant = ficheConsultation.getEtudiant();

        fiche_suivie.setDateRendezVous(new Date());



        // Vérifier et initialiser les listes si elles sont null
        if (fiche_suivie.getMedicamentListSuivie() == null) {
            fiche_suivie.setMedicamentListSuivie(new HashSet<>());
        }
        if (fiche_suivie.getSymptomeList() == null) {
            fiche_suivie.setSymptomeList(new HashSet<>());
        }
        if (fiche_suivie.getExamenList() == null) {
            fiche_suivie.setExamenList(new HashSet<>());
        }
        if (fiche_suivie.getDiagnostiqueList() == null) {
            fiche_suivie.setDiagnostiqueList(new HashSet<>());
        }



        // Ajouter les médicaments existants à la fiche de consultation
        if (idMedicament != null && !idMedicament.isEmpty()) {
            for (int i = 0; i < idMedicament.size(); i++) {
                Long medicamentId = idMedicament.get(i);
                int quantitePrescrite = quantiteMedicamentPrescrite.get(i); // Liste de quantités prescrites, doit être fournie depuis l'interface utilisateur
                String posologie = posologieList.get(i);
                Medicament medicament = medicamentRepository.findById(medicamentId)
                        .orElseThrow(() -> new IllegalArgumentException("Médicament non trouvé"));

                // Vérifier si la quantité prescrite est valide (supérieure à 0 et disponible en stock)
                if (quantitePrescrite > 0 && medicament.getQuantiteDisponible() >= quantitePrescrite) {
                    // Mettre à jour la quantité disponible
                    int nouvelleQuantiteDisponible = medicament.getQuantiteDisponible() - quantitePrescrite;
                    medicament.setQuantiteDisponible(nouvelleQuantiteDisponible);

                    // Ajouter le médicament à la liste des médicaments prescrits
                    MedicamentPrescrit medicamentPrescrit = new MedicamentPrescrit();
                    medicamentPrescrit.setMedicament(medicament);
                    medicamentPrescrit.setQuantitePrescrite(quantitePrescrite);
                    medicamentPrescrit.setPosogie(posologie);
                    medicamentPrescrit.setFicheSuivie(fiche_suivie);

                    fiche_suivie.getMedicamentListSuivie().add(medicamentPrescrit);
                } else {
                    // Gérer l'erreur si la quantité prescrite est invalide ou insuffisante en stock
                    throw new IllegalArgumentException("Quantité prescrite invalide ou insuffisante en stock pour le médicament : " + medicament.getNomMedicament());
                }
            }
        }



        // Ajouter les symptômes existants à la fiche de consultation
        if (idSymptome != null && !idSymptome.isEmpty()) {
            List<Symptome> symptomeList = symptomeRepository.findAllById(idSymptome);
            fiche_suivie.getSymptomeList().addAll(new ArrayList<>(symptomeList));
        }

        // Ajouter les examens existants à la fiche de consultation
        if (idExamen != null && !idExamen.isEmpty()) {
            List<Examen> examenList = examenRepository.findAllById(idExamen);
            fiche_suivie.getExamenList().addAll(new ArrayList<>(examenList));
        }

        // Ajouter les Diagnostiques existants à la fiche de consultation
        if (idDiagnostique != null && !idDiagnostique.isEmpty()) {
            List<Diagnostique> diagnostiqueList = diagnostiqueRepository.findAllById(idDiagnostique);
            fiche_suivie.getDiagnostiqueList().addAll(new ArrayList<>(diagnostiqueList));
        }

        // Ajouter les nouveaux symptômes à la fiche de consultation
        if (nouveauxSymptomes != null && !nouveauxSymptomes.isEmpty()) {
            for (String nouveauSymptome : nouveauxSymptomes) {
                Symptome symptome = new Symptome();
                symptome.setNomSymptome(nouveauSymptome);
                Symptome symptomeAjoute = symptomeRepository.save(symptome);
                fiche_suivie.getSymptomeList().add(symptomeAjoute);
            }
        }

        // Ajouter les nouveaux examens à la fiche de consultation
        if (nouveauxExamens != null && !nouveauxExamens.isEmpty()) {
            for (String nouveauExamen : nouveauxExamens) {
                Examen examen = new Examen();
                examen.setNomExamen(nouveauExamen);
                Examen examenAjoute = examenRepository.save(examen);
                fiche_suivie.getExamenList().add(examenAjoute);
            }
        }

        // Ajouter les nouveaux diagnostiques à la fiche de consultation
        if (nouveauxDiagnostique != null && !nouveauxDiagnostique.isEmpty()) {
            for (String nouveauDiagnostique : nouveauxDiagnostique) {
                Diagnostique diagnostique = new Diagnostique();
                diagnostique.setNomDiagnostique(nouveauDiagnostique);
                Diagnostique diagnostiqueAjoute = diagnostiqueRepository.save(diagnostique);
                fiche_suivie.getDiagnostiqueList().add(diagnostiqueAjoute);
            }
        }




        Set<MedicamentPrescrit> medicamentList = fiche_suivie.getMedicamentListSuivie();
        if (medicamentList != null && !medicamentList.isEmpty()) {
            // Créer une nouvelle facture associée à l'étudiant et à la fiche de consultation
            Facture facture = new Facture();
            facture.setDateCreationFacture(new Date());
            facture.setEtudiant(fiche_suivie.getFiche_consultation().getEtudiant());
            facture.setFicheSuivie(fiche_suivie);
            facture.setStatutFacture(false);

            // Vous pouvez ajouter d'autres propriétés de la facture ici selon vos besoins

            // Sauvegarder la nouvelle facture
            Facture factureCree = factureRepository.save(facture);

            // Maintenant, associez les médicaments prescrits à la nouvelle facture:

            int montantTotal = 0;

            List<MedicamentPrescrit> medicamentsPrescrits = new ArrayList<>();
            for (MedicamentPrescrit medicamentPrescrit : medicamentList) {
                medicamentsPrescrits.add(medicamentPrescrit);
                // Calculer le montant pour chaque médicament prescrit
                int quantitePrescrite = medicamentPrescrit.getQuantitePrescrite();
                int prixUnitaire = medicamentPrescrit.getMedicament().getPrixUnitaire();
                int montantMedicament = quantitePrescrite * prixUnitaire;

                // Ajouter le montant du médicament à la somme totale
                montantTotal += montantMedicament;
            }
            facture.setMontantFacute(montantTotal);



            // Sauvegarder les médicaments prescrits avec la référence à la nouvelle facture
            medicamentPrescritRepository.saveAll(medicamentsPrescrits);

            // Ajouter tous les médicaments prescrits à la nouvelle facture
            factureCree.setMedicamentPrescritsList(medicamentsPrescrits);
            for (MedicamentPrescrit medicamentPrescrit : medicamentsPrescrits) {
                medicamentPrescrit.setFacture(factureCree);
            }

            fiche_suivie.setFacture(factureCree);







            // Ajouter la facture à la liste des factures de l'étudiant
            List<Facture> facturesEtudiant = new ArrayList<>(fiche_suivie.getFiche_consultation().getEtudiant().getFactures());
            facturesEtudiant.add(factureCree);
            fiche_suivie.getFiche_consultation().getEtudiant().setFactures(facturesEtudiant);

            // Sauvegarder les modifications de l'étudiant
            etudiantRepository.save(fiche_suivie.getFiche_consultation().getEtudiant());
        }
        Fiche_Suivie ficheSuivie = ficheSuivieRepository.save(fiche_suivie);


        String emailContent;
        if (medicamentList != null && !medicamentList.isEmpty()) {

            Long NumFacture = fiche_suivie.getFacture().getNumFacture();
            File pieceJointe = rapportMetier.exportReportFile(NumFacture,"pdf");
            // Construisez le contenu de l'e-mail avec les informations de la consultation
            emailContent = "Cher parent,\n\n";
            emailContent += "Votre enfant " + fiche_suivie.getFiche_consultation().getEtudiant().getNom() + " a eu une consultation le "
                    + fiche_suivie.getDateRendezVous() + ". Voici les détails de la consultation :\n\n";

            // Ajout de la liste des symptômes
            if (fiche_suivie.getSymptomeList() != null && !fiche_suivie.getSymptomeList().isEmpty()) {
                emailContent += "Symptômes :\n";
                for (Symptome symptome : fiche_suivie.getSymptomeList()) {
                    emailContent += "- " + symptome.getNomSymptome() + "\n";
                }
                emailContent += "\n";
            }

            // Ajout de la liste des diagnostics
            if (fiche_suivie.getDiagnostiqueList() != null && !fiche_suivie.getDiagnostiqueList().isEmpty()) {
                emailContent += "Diagnostics :\n";
                for (Diagnostique diagnostique : fiche_suivie.getDiagnostiqueList()) {
                    emailContent += "- " + diagnostique.getNomDiagnostique() + "\n";
                }
                emailContent += "\n";
            }

            // Ajout de la liste des médicaments
            if (fiche_suivie.getMedicamentListSuivie() != null && !fiche_suivie.getMedicamentListSuivie().isEmpty()) {
                emailContent += "Médicaments prescrits :\n";
                for (MedicamentPrescrit medicamentPrescrit : fiche_suivie.getMedicamentListSuivie()) {
                    emailContent += "- " + medicamentPrescrit.getMedicament().getNomMedicament() + "\n";
                }
                emailContent += "\n";
            }

            // Ajout de la liste des examens
            if (fiche_suivie.getExamenList() != null && !fiche_suivie.getExamenList().isEmpty()) {
                emailContent += "Examens recommandés :\n";
                for (Examen examen : fiche_suivie.getExamenList()) {
                    emailContent += "- " + examen.getNomExamen() + "\n";
                }
                emailContent += "\n";
            }



            emailContent += "\n\nCordialement,\nVotre clinique";
            emailMessagePieceJointe.setContent(emailContent);
            emailMessagePieceJointe.setSubject("Rapport de consultation pour " + fiche_suivie.getFiche_consultation().getEtudiant().getNom());
            emailMessagePieceJointe.setPieceJointe(pieceJointe);
            emailMessagePieceJointe.setEmailEtudiant(etudiant.getEmailEtudiant());
            emailMessagePieceJointe.setEmailContactUrgence(etudiant.getEmailContactUrgence());


            if (emailManager.isConnectedToNetwork()) {
                try {
                    mailMetier.sendEmailToParentPieceJointeSuivie(etudiant.getEmailContactUrgence(), fiche_suivie,pieceJointe);
                    mailMetier.sendEmailToStudentPieceJointeSuivie(etudiant.getEmailEtudiant(),fiche_suivie,pieceJointe);

                } catch (MessagingException e) {
                    // Gérer les erreurs liées à l'envoi d'e-mails
                    e.printStackTrace();
                }

            } else {
                emailManager.addToEmailMessagePieceJointe(emailMessagePieceJointe);
            }


        } else {
            // Construisez le contenu de l'e-mail avec les informations de la consultation
            emailContent = "Cher parent,\n\n";
            emailContent += "Votre enfant " + fiche_suivie.getFiche_consultation().getEtudiant().getNom() + " a eu une consultation le "
                    + fiche_suivie.getDateRendezVous() + ". Voici les détails de la consultation :\n\n";

// Ajout de la liste des symptômes
            if (fiche_suivie.getSymptomeList() != null && !fiche_suivie.getSymptomeList().isEmpty()) {
                emailContent += "Symptômes :\n";
                for (Symptome symptome : fiche_suivie.getSymptomeList()) {
                    emailContent += "- " + symptome.getNomSymptome() + "\n";
                }
                emailContent += "\n";
            }

// Ajout de la liste des diagnostics
            if (fiche_suivie.getDiagnostiqueList() != null && !fiche_suivie.getDiagnostiqueList().isEmpty()) {
                emailContent += "Diagnostics :\n";
                for (Diagnostique diagnostique : fiche_suivie.getDiagnostiqueList()) {
                    emailContent += "- " + diagnostique.getNomDiagnostique() + "\n";
                }
                emailContent += "\n";
            }

// Ajout de la liste des examens
            if (fiche_suivie.getExamenList() != null && !fiche_suivie.getExamenList().isEmpty()) {
                emailContent += "Examens recommandés :\n";
                for (Examen examen : fiche_suivie.getExamenList()) {
                    emailContent += "- " + examen.getNomExamen() + "\n";
                }
                emailContent += "\n";
            }



            emailContent += "\n\nCordialement,\nVotre clinique";
            emailMessage.setContent(emailContent);
            emailMessage.setSubject("Rapport de consultation pour " + fiche_suivie.getFiche_consultation().getEtudiant().getNom());
            emailMessage.setEmailEtudiant(etudiant.getEmailEtudiant());
            emailMessage.setEmailContactUrgence(etudiant.getEmailContactUrgence());


            if (emailManager.isConnectedToNetwork()) {
                try {
                    mailMetier.sendEmailToParentSuivie(etudiant.getEmailContactUrgence(), fiche_suivie);
                    mailMetier.sendEmailToStudentSuivie(etudiant.getEmailEtudiant(), fiche_suivie);

                } catch (MessagingException e) {
                    // Gérer les erreurs liées à l'envoi d'e-mails
                    e.printStackTrace();
                }

            } else {
                emailManager.addToEmailMessage(emailMessage);
            }


        }


        if (fiche_suivie.getDateProchainRendezVous()!= null){
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String message = "Cher "+fiche_suivie.getFiche_consultation().getEtudiant().getNom()+",\n\n";
            message += "\"Rappel de rendez-vous à l'infirmerie \n\n " +
                    " Vous avez un rendez-vous à l'infirmerie demain a "+ timeFormat.format(ficheSuivie.getHeureProchainRendezVous())+ ".\n\n";

            message += "\n\nCordialement,Votre clinique";
            smsMessage.setNumero(fiche_suivie.getFiche_consultation().getEtudiant().getNumeroDeTelephone());
            smsMessage.setMessage(message);
            smsMessage.setDateProchainRendezVous(fiche_suivie.getDateProchainRendezVous());
            smsManager.addToSmsEnvoieDirect(smsMessage);

        }

        return ficheSuivie;
    }

}













