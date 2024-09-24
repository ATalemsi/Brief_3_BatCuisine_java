package main.java.com.baticuisine.dao.Imp;

import main.java.com.baticuisine.dao.ProjetDao;
import main.java.com.baticuisine.model.Client;
import main.java.com.baticuisine.model.MainOeuvre;
import main.java.com.baticuisine.model.Materiel;
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
    public int addProject(Projet projet) {
        String sql = "insert into projets(nom_projet,adresse,marge_beneficiaire,cout_total,etat_projet,id_client) values(?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, projet.getNomProjet());
            ps.setString(2, projet.getAdresse());
            ps.setDouble(3, projet.getMargeBeneficiaire());
            ps.setDouble(4, projet.getCoutTotal());
            String etatProjet = projet.getEtatProjet().name();
            System.out.println("Inserting projet with etat_projet: " + etatProjet);
            ps.setString(5, etatProjet);
            ps.setInt(6, projet.getIdclient());
            ps.executeUpdate();

            // Retrieve the generated project ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating project failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void updateProject(Projet projet ,int id) {
        String sql = "UPDATE projets SET nom_projet = ?, marge_beneficiaire = ?, cout_total = ?, etat_projet = ?, id_client = ? ,adresse =? ,WHERE id_projet = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, projet.getNomProjet());
            stmt.setDouble(2, projet.getMargeBeneficiaire());
            stmt.setDouble(3, projet.getCoutTotal());
            stmt.setString(4, projet.getEtatProjet().toString());
            stmt.setInt(5, projet.getIdclient());
            stmt.setString(6, projet.getAdresse());
            stmt.setInt(7,id);
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
         Projet projet = null;
        String sql = "SELECT * FROM projets WHERE id_projet = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                projet = new Projet(rs.getString("nom_projet"), rs.getDouble("marge_beneficiaire"), rs.getDouble("cout_total"), Projet.EtatProjet.valueOf(rs.getString("etat_projet").toUpperCase()), rs.getInt("id_client"), rs.getString("adresse"));
                projet.setId(rs.getInt("id_projet"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return projet;
    }

    @Override
    public List<Projet> getAllProjects() {
        String sql = "SELECT p.*, " +
                "c.nom AS client_nom, c.prenom AS client_prenom, c.adresse AS client_adresse, c.telephone AS client_telephone, c.est_professionnel AS client_professionnel, " +
                "m.nom AS materiel_nom,m.taux_tva AS materiel_taux_tva, m.quantite AS materiel_quantite, m.cout_unitaire AS materiel_cout_unitaire, m.coefficient_qualite AS materiel_qualite, m.cout_transport AS materiel_transport, " +
                "mo.nom AS main_oeuvre_nom,mo.taux_tva AS main_oeuvre_taux_tva, mo.taux_horaire AS main_oeuvre_taux_horaire, mo.heures_travail AS main_oeuvre_heures_travail, mo.productivite_ouvrier AS main_oeuvre_productivite " +
                "FROM projets p " +
                "INNER JOIN clients c ON p.id_client = c.id_client " +
                "LEFT JOIN materiel m ON p.id_projet = m.id_projet " +
                "LEFT JOIN main_d_oeuvre mo ON p.id_projet = mo.id_projet";

        List<Projet> projets = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // Retrieve project details
                int idProjet = rs.getInt("id_projet");
                String nomProjet = rs.getString("nom_projet");
                double margeBeneficiaire = rs.getDouble("marge_beneficiaire");
                double coutTotal = rs.getDouble("cout_total");
                Projet.EtatProjet etatProjet = Projet.EtatProjet.valueOf(rs.getString("etat_projet").toUpperCase());
                int idClient = rs.getInt("id_client");

                String nomClient = rs.getString("client_nom");
                String prenomClient = rs.getString("client_prenom");
                String adresseClient = rs.getString("client_adresse");
                String telephoneClient = rs.getString("client_telephone");
                boolean estProfessionnel = rs.getBoolean("client_professionnel");

                Client client = new Client(nomClient, prenomClient, adresseClient, telephoneClient, estProfessionnel);

                Projet projet = new Projet(nomProjet, margeBeneficiaire, coutTotal, etatProjet, idClient, client,adresseClient);

                List<Materiel> materiaux = new ArrayList<>();
                String materielNom = rs.getString("materiel_nom");
                if (materielNom != null) {
                    double materialTaux_TVA = rs.getDouble("materiel_taux_tva");
                    double materielQuantite = rs.getDouble("materiel_quantite");
                    double materielCoutUnitaire = rs.getDouble("materiel_cout_unitaire");
                    double materielQualite = rs.getDouble("materiel_qualite");
                    double materielTransport = rs.getDouble("materiel_transport");


                    Materiel materiel = new Materiel(materielNom,materialTaux_TVA,materielCoutUnitaire, materielQuantite,materielTransport, materielQualite,idProjet);
                    materiaux.add(materiel);
                }
                // Handle labor (Main d'œuvre)
                List<MainOeuvre> mainOeuvres = new ArrayList<>();
                String mainOeuvreNom = rs.getString("main_oeuvre_nom");
                if (mainOeuvreNom != null) {
                    double mainOevreTaux_TVA = rs.getDouble("main_oeuvre_taux_tva");
                    double mainOeuvreTauxHoraire = rs.getDouble("main_oeuvre_taux_horaire");
                    double mainOeuvreHeuresTravail = rs.getDouble("main_oeuvre_heures_travail");
                    double mainOeuvreProductivite = rs.getDouble("main_oeuvre_productivite");

                    MainOeuvre mainOeuvre = new MainOeuvre(mainOeuvreNom,mainOevreTaux_TVA, mainOeuvreTauxHoraire, mainOeuvreHeuresTravail, mainOeuvreProductivite,idProjet);
                    mainOeuvres.add(mainOeuvre);
                }
                projet.setMateriaux(materiaux);
                projet.setMainOeuvres(mainOeuvres);
                projets.add(projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projets;
    }




    public void updateCoutTotal(int id, double coutTotal,double marge) {
        String sql = "UPDATE projets SET cout_total = ?, marge_beneficiaire = ? WHERE id_projet = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, coutTotal);
            ps.setDouble(2, marge);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            System.out.println("rows affected" +rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Projet> getProjets() {
         String sql ="Select * from projets";
         List<Projet> projets = new ArrayList<>();
         try(PreparedStatement ps = connection.prepareStatement(sql)) {
             ResultSet rs = ps.executeQuery();
             while(rs.next()) {

                 String nomProjet = rs.getString("nom_projet");
                 double margeBeneficiaire = rs.getDouble("marge_beneficiaire");
                 double coutTotal = rs.getDouble("cout_total");
                 Projet.EtatProjet etatProjet = Projet.EtatProjet.valueOf(rs.getString("etat_projet").toUpperCase());
                 int idClient = rs.getInt("id_client");
                 String adresse = rs.getString("adresse");
                 Projet projet = new Projet(nomProjet, margeBeneficiaire, coutTotal, etatProjet, idClient,adresse);
                 projet.setId(rs.getInt("id_projet"));

              projets.add(projet);
             }

         }catch (SQLException e) {
             e.printStackTrace();
         }
         return projets;
    }

    public void updateProjectStatus(int projectId, Projet.EtatProjet etatProjet) {
        String sql = "UPDATE projets SET etat_projet = ? WHERE id_projet = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, etatProjet.name());
            ps.setInt(2, projectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
