package main.java.com.baticuisine.model;

public class Projet {
    private String nomProjet;
    private double margeBeneficiaire;
    private double coutTotal;
    private EtatProjet etatProjet;
    private int Idclient;

    public enum EtatProjet {
        EN_COURS,
        TERMINE,
        ANNULE
    }

    public Projet(String nomProjet,double margeBeneficiaire,double coutTotal,EtatProjet etatProjet,int Idclient) {
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etatProjet = etatProjet;
        this.Idclient = Idclient;
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

}

