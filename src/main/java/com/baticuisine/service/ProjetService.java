package main.java.com.baticuisine.service;

import main.java.com.baticuisine.dao.Imp.ProjetDaoImpl;
import main.java.com.baticuisine.model.Composant;
import main.java.com.baticuisine.model.MainOeuvre;
import main.java.com.baticuisine.model.Materiel;
import main.java.com.baticuisine.model.Projet;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

public class ProjetService {
    private static final Logger logger = Logger.getLogger(ProjetService.class.getName());
    private ProjetDaoImpl projetDao;


    public ProjetService(Connection connection) {
        this.projetDao = new ProjetDaoImpl(connection);
    }


    public int addProject(Projet projet) {
        int projectId = projetDao.addProject(projet);
        if (projectId != -1) {
            logger.info("Project added successfully with ID: " + projectId);
        } else {
            logger.info("Failed to add project.");
        }
        return projectId;  // Return the project ID
    }


    public void updateProject(Projet projet, int id) {
        Projet existingProject = projetDao.getProject(id);
        if (existingProject != null) {
            projetDao.updateProject(projet, id);
            logger.info("Project with ID " + id + " updated.");
        } else {
            logger.warning("Project with ID " + id + " does not exist.");
        }
    }


    public void deleteProject(int id) {
        Projet existingProject = projetDao.getProject(id);
        if (existingProject != null) {
            projetDao.deleteProject(id);
            logger.info("Project with ID " + id + " deleted.");
        } else {
            logger.warning("Project with ID " + id + " does not exist.");
        }
    }


    public Projet getProject(int id) {
        return projetDao.getProject(id);
    }


    public List<Projet> getAllProjects() {
        return projetDao.getAllProjects();
    }

    public double calculerCoutTotal(Projet projet,int id, List<Composant> composants, double tva, double marge) {
        double coutTotal = 0.0;

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

        projet.setCoutTotal(coutTotal);
        projet.setMargeBeneficiaire(marge);
        projetDao.updateCoutTotal(id, coutTotal,marge);
        return coutTotal;
    }
}
