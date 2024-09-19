package main.java.com.baticuisine.dao.Imp;

import main.java.com.baticuisine.dao.ProjetDao;
import main.java.com.baticuisine.model.Projet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetDaoImpl implements ProjetDao {
     private Connection connection;

     public ProjetDaoImpl(Connection connection) {
         this.connection = connection;
     }

    @Override
    public void addProject(Projet projet) {
         String sql = "insert into projet values(?,?,?,?,?)";
         try(PreparedStatement ps = connection.prepareStatement(sql)) {
             ps.setString(1, projet.getNomProjet());
             ps.setDouble(2, projet.getMargeBeneficiaire());
             ps.setDouble(3, projet.getCoutTotal());
             ps.setString(4, projet.getEtatProjet().toString());
             ps.setInt(5, projet.getIdclient());
             ps.executeUpdate();
         }catch (SQLException e){
             e.printStackTrace();
         }

    }

    @Override
    public void updateProject(Projet projet ,int id) {
        String sql = "UPDATE projets SET nom_projet = ?, marge_beneficiaire = ?, cout_total = ?, etat_projet = ?, id_client = ? WHERE id_projet = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, projet.getNomProjet());
            stmt.setDouble(2, projet.getMargeBeneficiaire());
            stmt.setDouble(3, projet.getCoutTotal());
            stmt.setString(4, projet.getEtatProjet().toString());
            stmt.setInt(5, projet.getIdclient());
            stmt.setInt(6,id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteProject(int id) {
        String sql = "DELETE FROM projets WHERE id_projet = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();

        }

    }

    @Override
    public Projet getProject(int id) {
        String sql = "SELECT * FROM projets WHERE id_projet = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Projet(rs.getString("nom_projet"), rs.getDouble("marge_beneficiaire"), rs.getDouble("cout_total"), Projet.EtatProjet.valueOf(rs.getString("etat_projet").toUpperCase()), rs.getInt("id_client"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Projet> getAllProjects() {
        String sql = "SELECT * FROM projets";
        List<Projet> projets = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                projets.add(new Projet(rs.getString("nom_projet"), rs.getDouble("marge_beneficiaire"), rs.getDouble("cout_total"),Projet.EtatProjet.valueOf(rs.getString("etat_projet").toUpperCase()), rs.getInt("id_client")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return projets;
    }
}
