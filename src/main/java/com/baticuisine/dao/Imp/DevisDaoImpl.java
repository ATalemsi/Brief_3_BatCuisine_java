package main.java.com.baticuisine.dao.Imp;

import main.java.com.baticuisine.dao.DevisDao;
import main.java.com.baticuisine.model.Devis;

import java.sql.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevisDaoImpl implements DevisDao {
    private Connection conn;

    public DevisDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertDevis(Devis devis) {
        String sql = "INSERT INTO devis (montant_estime, date_emission, date_validite, accepte, id_projet) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, devis.getMontantEstime());
            stmt.setDate(2, Date.valueOf(devis.getDateEmission()));
            stmt.setDate(3, Date.valueOf(devis.getDateValidite()));
            stmt.setBoolean(4, devis.isAccepte());
            stmt.setInt(5, devis.getIdProjet());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateDevis(Devis devis ,int id) {
        String sql = "UPDATE devis SET montant_estime = ?, date_emission = ?, date_validite = ?, accepte = ?, id_projet = ? WHERE id_devis = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, devis.getMontantEstime());
            stmt.setDate(2, Date.valueOf(devis.getDateEmission()));
            stmt.setDate(3, Date.valueOf(devis.getDateValidite()));
            stmt.setBoolean(4, devis.isAccepte());
            stmt.setInt(5, devis.getIdProjet());
            stmt.setInt(6, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteDevis(int id) {
        String sql = "DELETE FROM devis WHERE id_devis = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Devis getDevisById(int id) {
        String sql = "SELECT * FROM devis WHERE id_devis = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Devis(rs.getDouble("montant_estime"), rs.getDate("date_emission").toLocalDate(), rs.getDate("date_validite").toLocalDate(), rs.getBoolean("accepte"), rs.getInt("id_projet"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Devis> getAllDevis() {
        String sql = "SELECT * FROM devis";
        List<Devis> devisList = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                devisList.add(new Devis( rs.getDouble("montant_estime"), rs.getDate("date_emission").toLocalDate(), rs.getDate("date_validite").toLocalDate(), rs.getBoolean("accepte"), rs.getInt("id_projet")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return devisList;
    }

    public boolean updateDevisStatus(int devisId, boolean accepte) {
        String query = "UPDATE devis SET accepte = ? WHERE id_devis = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setBoolean(1, accepte);
            statement.setInt(2, devisId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if the update failed
        }
    }

    public Devis getDevisByProjectId(int projectId) {
        String query = "SELECT * FROM devis WHERE id_projet = ?";
        Devis devis = null;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                devis = new Devis(resultSet.getDouble("montant_estime"),resultSet.getDate("date_emission").toLocalDate(),resultSet.getDate("date_validite").toLocalDate(),resultSet.getBoolean("accepte"),resultSet.getInt("id_projet"));
                devis.setId(resultSet.getInt("id_devis"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devis;
    }

}
