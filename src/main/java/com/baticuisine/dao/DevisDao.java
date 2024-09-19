package main.java.com.baticuisine.dao;

import main.java.com.baticuisine.model.Devis;

import java.util.List;

public interface DevisDao {
     void insertDevis(Devis devis);
     void updateDevis(Devis devis,int id);
     void deleteDevis(int id);
     Devis getDevisById(int id);
     List<Devis> getAllDevis();

}
