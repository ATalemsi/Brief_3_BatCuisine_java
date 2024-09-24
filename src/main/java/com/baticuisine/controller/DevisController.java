package main.java.com.baticuisine.controller;

import main.java.com.baticuisine.model.Devis;
import main.java.com.baticuisine.service.DevisService;

import java.sql.Connection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevisController {
    private DevisService devisService;
    private static final Logger logger = LoggerFactory.getLogger(DevisController.class.getName());

    public DevisController(Connection connection) {
        this.devisService = new DevisService(connection);
    }

    public void addDevis(Devis devis) {
        devisService.addDevis(devis);
        logger.info("Devis added successfully.");
    }

    public void updateDevis(Devis devis, int id) {
        devisService.updateDevis(devis, id);
        logger.info( "Devis with ID "+id+" updated successfully.");
    }

    public void deleteDevis(int id) {
        devisService.deleteDevis(id);
        logger.info( "Devis with ID "+id+" deleted successfully.");
    }

    public Devis getDevisById(int id) {
        Devis devis = devisService.getDevisById(id);
        if (devis != null) {
            logger.info( "Fetched devis with ID"+id);
        } else {
            logger.warn("Devis with ID "+id+" does not exist.");
        }
        return devis;
    }

    public List<Devis> getAllDevis() {
        List<Devis> devisList = devisService.getAllDevis();
        logger.info( "Fetched all devis.");
        return devisList;
    }

    public Devis getDevisByProjectId(int projectId) {
        return devisService.getDevisByProjectId(projectId);
    }

    public void acceptDevis(int devisId) {
        devisService.acceptDevis(devisId);  // Update the 'accepté' attribute to true
    }
}
