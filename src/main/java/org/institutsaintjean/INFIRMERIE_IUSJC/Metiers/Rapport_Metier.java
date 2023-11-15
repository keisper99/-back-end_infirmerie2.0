package org.institutsaintjean.INFIRMERIE_IUSJC.Metiers;

import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Rapport_Metier {

    public String exportReport(Long NumFacture, String reportFormat) throws IOException, JRException;
    public File exportReportFile(Long NumFacture, String reportFormat) throws IOException, JRException;

//    public EtudiantDTO convertToDto(Etudiant etudiant);

    public String exportMedicamentRuture( String reportFormat) throws IOException, JRException;
    public File exportMedicamentRutureFile(String reportFormat) throws IOException, JRException;
}
