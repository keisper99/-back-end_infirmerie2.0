package org.institutsaintjean.INFIRMERIE_IUSJC.Controllers;


import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Diagnostique;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Symptome;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.DiagnostiqueRepository;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.SymptomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Infirmerie-IUSJC/Diagnostiques")
public class DiagnostiqueController {

    @Autowired
    private DiagnostiqueRepository diagnostiqueRepository;

    @GetMapping("/Liste")
    public List<Diagnostique> ListeDiagnostique() {
        return diagnostiqueRepository.findAll();
    }
}
