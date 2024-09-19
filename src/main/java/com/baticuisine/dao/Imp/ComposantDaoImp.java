package main.java.com.baticuisine.dao.Imp;

import main.java.com.baticuisine.dao.ComposantDao;
import main.java.com.baticuisine.model.Composant;
import main.java.com.baticuisine.model.Materiel;
import main.java.com.baticuisine.model.MainOeuvre;


import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComposantDaoImp implements ComposantDao {
    private Connection conn;

    public ComposantDaoImp(Connection conn) {
        this.conn = conn;
    }
    @Override
    public void addComposant(Composant composant) {
        if (composant instanceof Materiel) {
            String sql = "INSERT INTO materiel (nom, type_composant, taux_tva, cout_unitaire, quantite, cout_transport, coefficient_qualite, id_projet) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                Materiel materiel = (Materiel) composant;
                stmt.setString(1, materiel.getNom());
                stmt.setString(2, materiel.getTypeComposant());
                stmt.setDouble(3, materiel.getTauxTva());
                stmt.setDouble(4, materiel.getCoutUnitaire());
                stmt.setDouble(5, materiel.getQuantite());
                stmt.setDouble(6, materiel.getCoutTransport());
                stmt.setDouble(7, materiel.getCoefficientQualite());
                stmt.setInt(8, materiel.getIdProjet());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (composant instanceof MainOeuvre) {
            String sql = "INSERT INTO main_d_oeuvre (nom, type_composant, taux_tva, taux_horaire, heures_travail, productivite_ouvrier, id_projet) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                MainOeuvre mainDoeuvre = (MainOeuvre) composant;
                stmt.setString(1, mainDoeuvre.getNom());
                stmt.setString(2, mainDoeuvre.getTypeComposant());
                stmt.setDouble(3, mainDoeuvre.getTauxTva());
                stmt.setDouble(4, mainDoeuvre.getTauxHoraire());
                stmt.setDouble(5, mainDoeuvre.getHeuresTravail());
                stmt.setDouble(6, mainDoeuvre.getProductiviteOuvrier());
                stmt.setInt(7, mainDoeuvre.getIdProjet());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteComposant(int id) {
        String sql = "DELETE FROM composants WHERE id_composant = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateComposant(int id, Composant composant) {
        if (composant instanceof Materiel) {
            String sql = "UPDATE materiel SET nom = ?, taux_tva = ?, cout_unitaire = ?, quantite = ?, cout_transport = ?, coefficient_qualite = ?, id_projet = ? WHERE id_composant = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                Materiel materiel = (Materiel) composant;
                stmt.setString(1, materiel.getNom());
                stmt.setDouble(2, materiel.getTauxTva());
                stmt.setDouble(3, materiel.getCoutUnitaire());
                stmt.setDouble(4, materiel.getQuantite());
                stmt.setDouble(5, materiel.getCoutTransport());
                stmt.setDouble(6, materiel.getCoefficientQualite());
                stmt.setInt(7, materiel.getIdProjet());
                stmt.setInt(8, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (composant instanceof MainOeuvre) {
            String sql = "UPDATE main_d_oeuvre SET nom = ?, taux_tva = ?, taux_horaire = ?, heures_travail = ?, productivite_ouvrier = ?, id_projet = ? WHERE id_composant = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                MainOeuvre mainDoeuvre = (MainOeuvre) composant;
                stmt.setString(1, mainDoeuvre.getNom());
                stmt.setDouble(2, mainDoeuvre.getTauxTva());
                stmt.setDouble(3, mainDoeuvre.getTauxHoraire());
                stmt.setDouble(4, mainDoeuvre.getHeuresTravail());
                stmt.setDouble(5, mainDoeuvre.getProductiviteOuvrier());
                stmt.setInt(6, mainDoeuvre.getIdProjet());
                stmt.setInt(7, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Composant getComposantById(int id) {
        String sql = "SELECT * FROM composants WHERE id_composant = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type_composant");
                if ("Materiel".equals(type)) {
                    return new Materiel(rs.getString("nom"), rs.getDouble("taux_tva"), rs.getDouble("cout_unitaire"), rs.getDouble("quantite"), rs.getDouble("cout_transport"), rs.getDouble("coefficient_qualite"), rs.getInt("id_projet"));
                } else if ("Main-d'œuvre".equals(type)) {
                    return new MainOeuvre(rs.getString("nom"), rs.getDouble("taux_tva"), rs.getDouble("taux_horaire"), rs.getDouble("heures_travail"), rs.getDouble("productivite_ouvrier"), rs.getInt("id_projet"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Composant> getComposants() {
        List<Composant> composants = new ArrayList<>();
        String sqlMateriel = "SELECT * FROM materiel";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlMateriel);
            while (rs.next()) {
                composants.add(new Materiel(rs.getString("nom"), rs.getDouble("taux_tva"), rs.getDouble("cout_unitaire"), rs.getDouble("quantite"), rs.getDouble("cout_transport"), rs.getDouble("coefficient_qualite"), rs.getInt("id_projet")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sqlMainDoeuvre = "SELECT * FROM main_d_oeuvre";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlMainDoeuvre);
            while (rs.next()) {
                composants.add(new MainOeuvre(rs.getString("nom"), rs.getDouble("taux_tva"), rs.getDouble("taux_horaire"), rs.getDouble("heures_travail"), rs.getDouble("productivite_ouvrier"), rs.getInt("id_projet")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return composants;
    }
}

