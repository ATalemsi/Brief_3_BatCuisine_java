package main.java.com.baticuisine.service;
import main.java.com.baticuisine.dao.Imp.DevisDaoImpl;
import main.java.com.baticuisine.model.Devis;

import java.sql.Connection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevisService {
    private static final Logger logger = LoggerFactory.getLogger(DevisService.class.getName());
    private DevisDaoImpl devisDao;


    public DevisService(Connection connection) {
        this.devisDao = new DevisDaoImpl(connection);
    }

    public void addDevis(Devis devis) {
        devisDao.insertDevis(devis);
        logger.info("Devis added successfully: " + devis);
    }


    public void updateDevis(Devis devis, int id) {
        Devis existingDevis = devisDao.getDevisById(id);
        if (existingDevis != null) {
            devisDao.updateDevis(devis, id);
            logger.info("Devis with ID " + id + " updated successfully.");
        } else {
            logger.warn("Devis with ID " + id + " does not exist.");
        }
    }


    public void deleteDevis(int id) {
        Devis existingDevis = devisDao.getDevisById(id);
        if (existingDevis != null) {
            devisDao.deleteDevis(id);
            logger.info("Devis with ID " + id + " deleted successfully.");
        } else {
            logger.warn("Devis with ID " + id + " does not exist.");
        }
    }


    public Devis getDevisById(int id) {
        return devisDao.getDevisById(id);
    }

    // Method to get all Devis entries
    public List<Devis> getAllDevis() {
        return devisDao.getAllDevis();
    }
    public Devis getDevisByProjectId(int projectId) {
        return devisDao.getDevisByProjectId(projectId);
    }
    public void acceptDevis(int devisId) {
        devisDao.updateDevisStatus(devisId, true);  // Update the 'accepté' attribute to true
    }
}
