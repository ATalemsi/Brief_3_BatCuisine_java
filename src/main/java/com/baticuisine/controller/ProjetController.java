package main.java.com.baticuisine.controller;

import main.java.com.baticuisine.dao.Imp.ComposantDaoImp;
import main.java.com.baticuisine.model.*;
import main.java.com.baticuisine.service.ProjetService;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjetController {
    private ProjetService projetService;
    private static final Logger logger = LoggerFactory.getLogger(ProjetController.class.getName());

    public ProjetController(Connection connection) {
        this.projetService = new ProjetService(connection);
    }

    public int addProject(Projet projet) {
        int projectId = projetService.addProject(projet);
        if (projectId != -1) {
            logger.info("Project added successfully with ID: " + projectId);
        } else {
            logger.info("Failed to add project.");
        }
        return projectId;
    }

    public void updateProject(Projet projet, int id) {
        projetService.updateProject(projet, id);
        logger.info("Project with ID " + id + " updated successfully.");
    }

    public void deleteProject(int id) {
        projetService.deleteProject(id);
        logger.info("Project with ID " + id + " deleted successfully.");
    }

    public Projet getProjectById(int id) {
        Projet projet = projetService.getProject(id);
        if (projet != null) {
            logger.info("Fetched project with ID " + id);
        } else {
            logger.warn("Project with ID " + id + " does not exist.");
        }
        return projet;
    }

    public void getAllProjects() {
        List<Projet> projets = projetService.getAllProjects();  // Fetch all projects using the service
        if (projets.isEmpty()) {
            logger.warn("No projects found.");
        } else {
            afficherDetailsTousLesProjets(projets);  // Call the method to display all project details
        }
    }

    private void afficherDetailsTousLesProjets(List<Projet> projets) {
        for (Projet projet : projets) {
            afficherDetailsProjet(projet);  // Display details for each project
            System.out.println("===========================================");
        }
    }

    // Method to display details of a single project
    private void afficherDetailsProjet(Projet projet) {
        double coutMateriauxAvantTVA = projet.calculerCoutMateriauxAvantTVA();
        double coutMateriauxAvecTVA = coutMateriauxAvantTVA * 1.20;

        double coutMainOeuvreAvantTVA = projet.calculerCoutMainOeuvreAvantTVA();
        double coutMainOeuvreAvecTVA = coutMainOeuvreAvantTVA * 1.20;  // 20% TVA

        double coutTotalAvantMarge = projet.calculerCoutTotalAvantMarge();
        double margeBeneficiaire = projet.calculerMarge();
        double coutTotalFinal = projet.calculerCoutTotalFinal();


        System.out.println("Nom du projet : " + projet.getNomProjet());
        System.out.println("Client : " + projet.getClient().getNom() + " " + projet.getClient().getPrenom());
        System.out.println("Adresse du chantier : " + projet.getAdresse());

        System.out.println("--- Détail des Coûts ---");


        System.out.println("1. Matériaux : ");
        projet.getMateriaux().stream()
                .forEach(m -> System.out.println("- " + m.getNom() + " : " + String.format("%.2f", m.calculerCout())
                        + " MAD (quantité : " + m.getQuantite() + ", coût unitaire : " + String.format("%.2f", m.getCoutUnitaire())
                        + " MAD, qualité : " + m.getCoefficientQualite() + ", transport : " + String.format("%.2f", m.getCoutTransport()) + " MAD)"));

        System.out.println("**Coût total des matériaux avant TVA : " + String.format("%.2f", coutMateriauxAvantTVA) + " MAD**");
        System.out.println("**Coût total des matériaux avec TVA (20%) : " + String.format("%.2f", coutMateriauxAvecTVA) + " MAD**");


        System.out.println("2. Main-d'œuvre : ");
        projet.getMainOeuvres().stream()
                .forEach(mo -> System.out.println("- " + mo.getNom() + " : " + String.format("%.2f", mo.calculerCout())
                        + " MAD (taux horaire : " + String.format("%.2f", mo.getTauxHoraire())
                        + " MAD/h, heures travaillées : " + mo.getHeuresTravail() + " h, productivité : " + mo.getProductiviteOuvrier() + ")"));

        System.out.println("**Coût total de la main-d'œuvre avant TVA : " + String.format("%.2f", coutMainOeuvreAvantTVA) + " MAD**");
        System.out.println("**Coût total de la main-d'œuvre avec TVA (20%) : " + String.format("%.2f", coutMainOeuvreAvecTVA) + " MAD**");

        System.out.println("3. Coût total avant marge : " + String.format("%.2f", coutTotalAvantMarge) + " MAD");
        System.out.println("4. Marge bénéficiaire (" + String.format("%.2f", projet.getMargeBeneficiaire() * 100) + "%) : " + String.format("%.2f", margeBeneficiaire) + " MAD");
        System.out.println("**Coût total final du projet : " + String.format("%.2f", coutTotalFinal) + " MAD**");
    }


    public double calculerCoutTotal(Projet projet, int id, List<Composant> composants, double tva, double marge, Client client) {
        return projetService.calculerCoutTotal(projet,id, composants, tva, marge,client);
    }
    public List<Projet> getProjets() {
        return projetService.getProjets();
    }

    public void updateProjectStatus(int projectId, Projet.EtatProjet etatProjet) {
        projetService.updateProjectStatus(projectId, etatProjet);
        logger.info("Le statut du projet avec ID " + projectId + " a été mis à jour à " + etatProjet);
    }
}


