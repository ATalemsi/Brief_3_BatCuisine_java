package main.java.com.baticuisine.controller;

import main.java.com.baticuisine.model.Composant;
import main.java.com.baticuisine.service.ComposantService;

import java.sql.Connection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComposantController {
    private ComposantService composantService;
    private static final Logger logger = LoggerFactory.getLogger(ComposantController.class.getName());

    public ComposantController(Connection connection) {
        this.composantService = new ComposantService(connection);
    }

    public void addComposant(Composant composant) {
        composantService.addComposant(composant);
        logger.info("Composant added successfully.");
    }

    public void updateComposant(Composant composant, int id) {
        composantService.updateComposant(id,composant);
        logger.info( "Composant with ID "+id+" updated successfully.");
    }

    public void deleteComposant(int id) {
        composantService.deleteComposant(id);
        logger.info("Composant with ID "+id+" deleted successfully.");
    }

    public Composant getComposantById(int id) {
        Composant composant = composantService.getComposantById(id);
        if (composant != null) {
            logger.info("Fetched composant with ID "+id);
        } else {
            logger.warn( "Composant with ID "+id+" does not exist.");
        }
        return composant;
    }

    public List<Composant> getAllComposants() {
        List<Composant> composants = composantService.getAllComposants();
        logger.info( "Fetched all composants.");
        return composants;
    }
}
