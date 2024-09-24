package main.java.com.baticuisine.service;

import main.java.com.baticuisine.dao.Imp.ProjetDaoImpl;
import main.java.com.baticuisine.model.*;

import java.sql.Connection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjetService {
    private static final Logger logger = LoggerFactory.getLogger(ProjetService.class.getName());
    private ProjetDaoImpl projetDao;


    public ProjetService(Connection connection) {
        this.projetDao = new ProjetDaoImpl(connection);
    }


    public int addProject(Projet projet) {
        int projectId = projetDao.addProject(projet);
        return projectId;
    }


    public void updateProject(Projet projet, int id) {
        Projet existingProject = projetDao.getProject(id);
        if (existingProject != null) {
            projetDao.updateProject(projet, id);
            logger.info("Project with ID " + id + " updated.");
        } else {
            logger.info("Project with ID " + id + " does not exist.");
        }
    }


    public void deleteProject(int id) {
        Projet existingProject = projetDao.getProject(id);
        if (existingProject != null) {
            projetDao.deleteProject(id);
            logger.info("Project with ID " + id + " deleted.");
        } else {
            logger.info("Project with ID " + id + " does not exist.");
        }
    }


    public Projet getProject(int id) {
        return projetDao.getProject(id);
    }


    public List<Projet> getAllProjects() {
        return projetDao.getAllProjects();
    }

    public double calculerCoutTotal(Projet projet, int id, List<Composant> composants, double tva, double marge, Client client) {
        double coutTotal = 0.0;

        // Calculate total component costs
        for (Composant composant : composants) {
            if (composant instanceof Materiel) {
                Materiel materiel = (Materiel) composant;
                double coutMateriel = materiel.getCoutUnitaire() * materiel.getQuantite() + materiel.getCoutTransport();
                coutTotal += coutMateriel;
            } else if (composant instanceof MainOeuvre) {
                MainOeuvre mainDoeuvre = (MainOeuvre) composant;
                double coutMainOeuvre = mainDoeuvre.getTauxHoraire() * mainDoeuvre.getHeuresTravail();
                coutTotal += coutMainOeuvre;
            }
        }
        coutTotal += coutTotal * tva;
        coutTotal += coutTotal * marge;
        if (client.isEstProfessionnel()) {
            coutTotal *= 0.90;
        } else {

            coutTotal *= 0.95;
        }
        projet.setCoutTotal(coutTotal);
        projet.setMargeBeneficiaire(marge);
        projetDao.updateCoutTotal(id, coutTotal, marge);

        return coutTotal;
    }

    public List<Projet> getProjets() {
        return projetDao.getProjets();
    }

    public void updateProjectStatus(int projectId, Projet.EtatProjet etatProjet) {
        projetDao.updateProjectStatus(projectId, etatProjet);
    }
}
