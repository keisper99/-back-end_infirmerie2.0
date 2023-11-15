package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;



import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Etudiant;

import java.util.List;

public interface Etudiant_Metier {
    public Etudiant ajouterEtudiant(Long idInfirmiere, Etudiant etudiant, List<Long> antecedantMedicauxIds, List<String> nouveauxAntecedantsMedicaux);
    public Etudiant modifierEtudiant( Long id, Etudiant etudiantModifie, List<Long> antecedantMedicauxIds, List<String> nouveauxAntecedantsMedicaux);
    public Etudiant rechercherEtudiant(Long id);
    public Etudiant supprimerEtudiant(Long id);
    public List<Etudiant> getEtudiantsNonBloques();

}
