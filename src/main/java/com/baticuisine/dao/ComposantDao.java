package main.java.com.baticuisine.dao;

import main.java.com.baticuisine.model.Composant;

import java.util.List;

public interface ComposantDao {
    void addComposant(Composant composant);
    void deleteComposant(int id);
    void updateComposant(int id,Composant composant);
    Composant getComposantById(int id);
    List<Composant> getComposants();
}
