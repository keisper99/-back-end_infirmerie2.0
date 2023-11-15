package org.institutsaintjean.INFIRMERIE_IUSJC.Controllers;

import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Examen;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Symptome;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.ExamenRepository;
import org.institutsaintjean.INFIRMERIE_IUSJC.Repositories.SymptomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Infirmerie-IUSJC/Examens")

public class ExamenController {

    @Autowired
    private ExamenRepository examenRepository;

    @GetMapping("/Liste")
    public List<Examen> ListeExamens() {
        return examenRepository.findAll();
    }
}
