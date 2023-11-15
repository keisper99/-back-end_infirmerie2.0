package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;



import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Medicament;

import java.util.List;

public interface Medicament_Metier {
    public Medicament supprimerMedicament(Long idMedicament);

    public List<Medicament> getMedicamentsNonsupprimer();

    public Medicament modifierMedicament(Long idMedicament, Medicament nouveauMedicament);
    public boolean peutPrescrireMedicament(Long idMedicamentPrescrit, Integer quantitePrescrite);
}
