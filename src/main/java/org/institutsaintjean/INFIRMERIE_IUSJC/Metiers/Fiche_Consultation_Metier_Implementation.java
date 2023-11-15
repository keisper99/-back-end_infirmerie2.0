package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;

import lombok.RequiredArgsConstructor;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Consultation;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Fiche_Suivie;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.Fiche_SuivieRepository;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.Fiche_consultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class Fiche_Consultation_Metier_Implementation implements Fiche_Consultation_Metier{


}
