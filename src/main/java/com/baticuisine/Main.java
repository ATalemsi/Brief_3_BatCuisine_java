package main.java.com.baticuisine;

import main.java.com.baticuisine.Presentation.GestionProjetsCLI;
import main.java.com.baticuisine.db.DatabaseConnection;

import java.sql.Connection;
public class Main {
    public static void main(String[] args) {
        try {
            GestionProjetsCLI gestionProjetsCLI = new GestionProjetsCLI(DatabaseConnection.getConnection());
            gestionProjetsCLI.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}