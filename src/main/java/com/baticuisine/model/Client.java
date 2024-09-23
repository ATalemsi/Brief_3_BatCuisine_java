package main.java.com.baticuisine.model;

public class Client {
    private int id_client;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private boolean estProfessionnel;


    public Client(String nom, String prenom, String adresse, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.estProfessionnel = false;


    }

    public Client(String nom,String prenom, String adresse, String telephone, boolean estProfessionnel) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.estProfessionnel = estProfessionnel;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public boolean isEstProfessionnel() {
        return estProfessionnel;
    }
    public void setEstProfessionnel(boolean estProfessionnel) {
        this.estProfessionnel = estProfessionnel;
    }
    public int getId_client() {
        return id_client;
    }
    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
}
