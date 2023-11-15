
package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.MedicamentPrescritDTO;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.*;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.FactureRepository;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class Rapport_metier_implementation implements Rapport_Metier {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private MedicamentRepository medicamentRepository;
    /*private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public EtudiantDTO convertToDto(Etudiant etudiant) {
        return modelMapper.map(etudiant, EtudiantDTO.class);
    }*/

    public String exportReport(Long NumFacture, String reportFormat) throws IOException, JRException {

        String path = System.getProperty("user.home") + "\\Desktop\\Rapport"; // Chemin du bureau

        // Vérifier si le dossier existe, sinon le créer
        Path folderPath = Paths.get(path);
        if (!Files.exists(folderPath)) {
            Files.createDirectory(folderPath);
        }

        // Remplacer "Facture" par le nom de l'entité correspondant à votre table de factures
        Optional<Facture> optionalFacture = factureRepository.findById(NumFacture);
        if (optionalFacture.isEmpty()) {
            throw new IllegalArgumentException("La facture avec l'ID " + NumFacture + " n'a pas été trouvée.");
        }
        Facture facture = optionalFacture.get();

        // Effectuer la jointure avec la table des étudiants pour récupérer les informations de l'étudiant
        Etudiant etudiant = facture.getEtudiant();
        Fiche_Consultation ficheConsultation = facture.getFicheConsultation();


//        EtudiantDTO etudiantDTO = convertToDto(etudiant);

        /*EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setNom(etudiant.getNom());
        etudiantDTO.setMatricule(etudiant.getMatricule());*/

        // Effectuer la jointure avec la table des médicaments pour récupérer la liste des médicaments associés à la facture
        List<MedicamentPrescrit> medicaments = facture.getMedicamentPrescritsList();
        if (medicaments.isEmpty()) {
            throw new IllegalStateException("Aucun médicament prescrit n'a été trouvé pour cette facture.");
        }
        List<MedicamentPrescritDTO> medicamentsPrescritsDTO = new ArrayList<>();
        for (MedicamentPrescrit medicamentPrescrit : medicaments) {
            Medicament medicament = medicamentPrescrit.getMedicament();
            MedicamentPrescritDTO medicamentPrescritDTO = new MedicamentPrescritDTO();
            medicamentPrescritDTO.setNomMedicament(medicament.getNomMedicament());
            //medicamentPrescritDTO.setDosage(medicament.getDosage());
            // Ajoutez les autres attributs nécessaires de l'entité Medicament

            medicamentPrescritDTO.setQuantitePrescrite(medicamentPrescrit.getQuantitePrescrite());
            medicamentPrescritDTO.setPrixUnitaire(medicament.getPrixUnitaire());
            // Ajoutez les autres attributs nécessaires de l'entité MedicamentPrescrit

            medicamentsPrescritsDTO.add(medicamentPrescritDTO);
        }

// Afficher le contenu de la liste des médicaments dans la console (optionnel, pour débogage)
        System.out.println("Liste des médicaments prescrits :");
        for (MedicamentPrescrit medicamentPrescrit : medicaments) {
            System.out.println(medicamentPrescrit.getMedicament().getNomMedicament());
        }


        File file = ResourceUtils.getFile("classpath:A4_1.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Belom");
        // parameters.put("factureId", facture.getNumFacture()); // Passer l'ID de la facture pour le filtrage
        parameters.put("etudiant", etudiant); // Passer l'objet Etudiant pour le mappage dans le rapport
        parameters.put("facture", facture);
        parameters.put("medicamentLists", medicamentsPrescritsDTO); // Passer la liste des médicaments pour le mappage dans le rapport

        // Ici, nous utilisons Collections.singletonList pour créer une liste contenant uniquement la facture cible
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(facture));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\Facture" + etudiant.getNom() + ".pdf");
        }

        return "Facture générée dans : " + path;
    }

    @Override
    public File exportReportFile(Long NumFacture, String reportFormat) throws IOException, JRException {
        String path = System.getProperty("user.home") + "\\Desktop\\RapportMail"; // Chemin du bureau

        // Vérifier si le dossier existe, sinon le créer
        Path folderPath = Paths.get(path);
        if (!Files.exists(folderPath)) {
            Files.createDirectory(folderPath);
        }


        // Remplacer "Facture" par le nom de l'entité correspondant à votre table de factures
        Optional<Facture> optionalFacture = factureRepository.findById(NumFacture);
        if (!optionalFacture.isPresent()) {
            throw new IllegalArgumentException("La facture avec l'ID " + NumFacture + " n'a pas été trouvée.");
        }
        Facture facture = optionalFacture.get();

        // Effectuer la jointure avec la table des étudiants pour récupérer les informations de l'étudiant
        Etudiant etudiant = facture.getEtudiant();
        Fiche_Consultation ficheConsultation = facture.getFicheConsultation();


//        EtudiantDTO etudiantDTO = convertToDto(etudiant);

        /*EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setNom(etudiant.getNom());
        etudiantDTO.setMatricule(etudiant.getMatricule());*/

        // Effectuer la jointure avec la table des médicaments pour récupérer la liste des médicaments associés à la facture
        List<MedicamentPrescrit> medicaments = facture.getMedicamentPrescritsList();
        if (medicaments.isEmpty()) {
            throw new IllegalStateException("Aucun médicament prescrit n'a été trouvé pour cette facture.");
        }
        List<MedicamentPrescritDTO> medicamentsPrescritsDTO = new ArrayList<>();
        for (MedicamentPrescrit medicamentPrescrit : medicaments) {
            Medicament medicament = medicamentPrescrit.getMedicament();
            MedicamentPrescritDTO medicamentPrescritDTO = new MedicamentPrescritDTO();
            medicamentPrescritDTO.setNomMedicament(medicament.getNomMedicament());
            //medicamentPrescritDTO.setDosage(medicament.getDosage());
            // Ajoutez les autres attributs nécessaires de l'entité Medicament

            medicamentPrescritDTO.setQuantitePrescrite(medicamentPrescrit.getQuantitePrescrite());
            medicamentPrescritDTO.setPrixUnitaire(medicament.getPrixUnitaire());
            // Ajoutez les autres attributs nécessaires de l'entité MedicamentPrescrit

            medicamentsPrescritsDTO.add(medicamentPrescritDTO);
        }

// Afficher le contenu de la liste des médicaments dans la console (optionnel, pour débogage)
        System.out.println("Liste des médicaments prescrits :");
        for (MedicamentPrescrit medicamentPrescrit : medicaments) {
            System.out.println(medicamentPrescrit.getMedicament().getNomMedicament());
        }


        File file = ResourceUtils.getFile("classpath:A4_1.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Belom");
        parameters.put("facture", facture);
        //parameters.put("factureId", facture.getNumFacture()); // Passer l'ID de la facture pour le filtrage
        parameters.put("etudiant", etudiant); // Passer l'objet Etudiant pour le mappage dans le rapport
        parameters.put("medicamentLists", medicamentsPrescritsDTO); // Passer la liste des médicaments pour le mappage dans le rapport

        // Ici, nous utilisons Collections.singletonList pour créer une liste contenant uniquement la facture cible
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(facture));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\Facture" + etudiant.getNom() + ".pdf");
        }
        File invoiceFile = new File(path + "\\Facture" + etudiant.getNom() + ".pdf");

        return invoiceFile;

    }

    @Override
    public String exportMedicamentRuture(String reportFormat) throws IOException, JRException {

        String path = System.getProperty("user.home") + "\\Desktop\\Rapport"; // Chemin du bureau

        // Vérifier si le dossier existe, sinon le créer
        Path folderPath = Paths.get(path);
        if (!Files.exists(folderPath)) {
            Files.createDirectory(folderPath);
        }

        // Récupérer la liste des médicaments en rupture de stock
        List<Medicament> medicamentsRuptureStock = medicamentRepository.findMedicamentsRuptureDeStock();

        if (medicamentsRuptureStock.isEmpty()) {
            return "Aucun médicament en rupture de stock.";
        }

        // Chargement du fichier JasperReport pour le rapport MedicamentRupture.jrxml
        File file = ResourceUtils.getFile("classpath:MedicamentRupture.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Belom");
        parameters.put("medicamentsRuptureStock", medicamentsRuptureStock);

        // Création d'une source de données JRBeanCollectionDataSource avec la liste des médicaments en rupture de stock
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(medicamentsRuptureStock);

        // Remarque : assurez-vous que les champs dans MedicamentRupture.jrxml correspondent aux noms de vos attributs dans la classe Medicament

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Exporter le rapport au format PDF ou autre format selon la valeur de reportFormat
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\MedicamentsRuptureStock.pdf");
        }

        return "Rapport des médicaments en rupture de stock généré dans : " + path;

    }

    @Override
    public File exportMedicamentRutureFile(String reportFormat) throws IOException, JRException {

        String path = System.getProperty("user.home") + "\\Desktop\\RapportMail"; // Chemin du bureau

        // Vérifier si le dossier existe, sinon le créer
        Path folderPath = Paths.get(path);
        if (!Files.exists(folderPath)) {
            Files.createDirectory(folderPath);
        }

        // Récupérer les médicaments en rupture de stock
        List<Medicament> medicamentsRuptureDeStock = medicamentRepository.findMedicamentsRuptureDeStock();

        // Charger le fichier jrxml pour le rapport MedicamentRupture.jrxml
        File file = ResourceUtils.getFile("classpath:MedicamentRupture.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Belom");
        parameters.put("medicamentsRuptureDeStock", medicamentsRuptureDeStock);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(medicamentsRuptureDeStock);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\MedicamentsRuptureDeStock.pdf");
        }

        File reportFile = new File(path + "\\MedicamentsRuptureDeStock.pdf");
        return reportFile;

    }
}

