package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Facture;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@Transactional
public class FactureMetierImplementation implements Facture_Metier{

    @Autowired
    private FactureRepository factureRepository;
    @Override
    public Facture RegleFacture(Long NumFacture) {
        Facture facture = factureRepository.findById(NumFacture).get();
        if(facture==null) throw new RuntimeException("Facture introuvable");

        facture.setStatutFacture(true);
        return factureRepository.save(facture);

    }

}
