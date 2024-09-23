package main.java.com.baticuisine.service;

import main.java.com.baticuisine.dao.Imp.ComposantDaoImp;
import main.java.com.baticuisine.model.Composant;

import java.sql.Connection;
import java.util.List;

public class ComposantService {
    private ComposantDaoImp composantDaoImp;

    public ComposantService(Connection conn) {
        this.composantDaoImp = new ComposantDaoImp(conn);
    }

    public void addComposant(Composant composant) {
        composantDaoImp.addComposant(composant);
    }

    public void updateComposant(int id, Composant composant) {
        composantDaoImp.updateComposant(id, composant);
    }

    public void deleteComposant(int id) {
        composantDaoImp.deleteComposant(id);
    }

    public Composant getComposantById(int id) {
        return composantDaoImp.getComposantById(id);
    }

    public List<Composant> getAllComposants() {
        return composantDaoImp.getComposants();
    }
}
