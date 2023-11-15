


package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;

import net.sf.jasperreports.engine.JRException;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.CredentialsDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.InfirmiereDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.SignUpDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Consultation;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Suivie;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Infirmiere;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Medicament;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Infirmiere_Metier {

public Medicament ajouterMedicament(Long idMedicament, Infirmiere infirmiere, Medicament medicament);

    public InfirmiereDto login(CredentialsDto credentialsDto);
    public InfirmiereDto register(SignUpDto userDto);
    public InfirmiereDto findByLogin(String login) ;

    public InfirmiereDto updateLoginAndPassword(Long infirmiereId, String newLogin, String newPassword);

 public Fiche_Consultation creerFicheConsultation(Long idEtudiant, Long idInfirmiere, Fiche_Consultation fiche_consultation,
                                                  List<Long> idMedicament, List<Long> idSymptome, List<Long> idExamen,
                                                  List<Long> idDiagnostique, List<Integer> quantiteMedicamentPrescrite,List<String> posologieList,
                                                  List<String> nouveauxSymptomes, List<String> nouveauxExamens, List<String> nouveauxDiagnostique) throws JRException, IOException;








    public Fiche_Suivie creerFicheSuivie(Long idFicheConsultation, Long idInfirmiere, Fiche_Suivie fiche_suivie,
                                         List<Long> idMedicament, List<Long> idSymptome, List<Long> idExamen,
                                         List<Long> idDiagnostique, List<Integer> quantiteMedicamentPrescrite,List<String> posologieList,
                                         List<String> nouveauxSymptomes, List<String> nouveauxExamens, List<String> nouveauxDiagnostique) throws JRException, IOException;










}



