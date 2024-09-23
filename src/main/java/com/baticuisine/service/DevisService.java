package main.java.com.baticuisine.service;
import main.java.com.baticuisine.dao.Imp.DevisDaoImpl;
import main.java.com.baticuisine.model.Devis;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

public class DevisService {
    private static final Logger logger = Logger.getLogger(DevisService.class.getName());
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
            logger.warning("Devis with ID " + id + " does not exist.");
        }
    }


    public void deleteDevis(int id) {
        Devis existingDevis = devisDao.getDevisById(id);
        if (existingDevis != null) {
            devisDao.deleteDevis(id);
            logger.info("Devis with ID " + id + " deleted successfully.");
        } else {
            logger.warning("Devis with ID " + id + " does not exist.");
        }
    }


    public Devis getDevisById(int id) {
        return devisDao.getDevisById(id);
    }

    // Method to get all Devis entries
    public List<Devis> getAllDevis() {
        return devisDao.getAllDevis();
    }
}
