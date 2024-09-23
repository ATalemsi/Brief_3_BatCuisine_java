package main.java.com.baticuisine.model;

import java.util.ArrayList;
import java.util.List;

public class Projet {
    private int id;
    private String nomProjet;
    private double margeBeneficiaire;
    private double coutTotal;
    private EtatProjet etatProjet;
    private String adresse;
    private int Idclient;
    private Client client;
    private List<Materiel> materiaux;  // List of materials
    private List<MainOeuvre> mainOeuvres;

    public enum EtatProjet {
        EN_COURS,
        TERMINE,
        ANNULE
    }

    public Projet(String nomProjet,double margeBeneficiaire,double coutTotal,EtatProjet etatProjet,int Idclient,String adresse) {
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etatProjet = etatProjet;
        this.Idclient = Idclient;
        this.adresse = adresse;
    }
    public Projet(String nomProjet,double margeBeneficiaire,double coutTotal,EtatProjet etatProjet,int Idclient,Client client,String adresse) {
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etatProjet = etatProjet;
        this.Idclient = Idclient;
        this.client = client;
        this.adresse = adresse;
        this.materiaux = new ArrayList<>();
        this.mainOeuvres = new ArrayList<>();

    }
    public void setMateriaux(List<Materiel> materiaux) {
        this.materiaux = materiaux;
    }

    public List<Materiel> getMateriaux() {
        return materiaux;
    }

    public void setMainOeuvres(List<MainOeuvre> mainOeuvres) {
        this.mainOeuvres = mainOeuvres;
    }

    public List<MainOeuvre> getMainOeuvres() {
        return mainOeuvres;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNomProjet() {
        return nomProjet;
    }
    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }
    public double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }
    public void setMargeBeneficiaire(double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }
    public double getCoutTotal() {
        return coutTotal;
    }
    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }
    public EtatProjet getEtatProjet() {
        return etatProjet;
    }

    public int getIdclient() {
        return Idclient;
    }
    public void setIdclient(int idclient) {
        this.Idclient = idclient;
    }

    public double calculerCoutMateriauxAvantTVA() {
        return materiaux.stream().mapToDouble(Materiel::calculerCout).sum();
    }

    public double calculerCoutMainOeuvreAvantTVA() {
        return mainOeuvres.stream().mapToDouble(MainOeuvre::calculerCout).sum();
    }

    public double calculerMarge() {
        return calculerCoutTotalAvantMarge() * margeBeneficiaire;
    }

    public double calculerCoutTotalAvantMarge() {
        return calculerCoutMateriauxAvantTVA() + calculerCoutMainOeuvreAvantTVA();
    }

    public double calculerCoutTotalFinal() {
        return calculerCoutTotalAvantMarge() + calculerMarge();
    }

}

