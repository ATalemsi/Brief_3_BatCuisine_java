package main.java.com.baticuisine.model;

import java.util.Date;

public class Devis {
    private int id;
    private double montantEstime;
    private Date dateEmission;
    private Date dateValidite;
    private boolean accepte;
    private int IdProjet;

    public Devis( double montantEstime, Date dateEmission, Date dateValidite, boolean accepte,int IdProjet) {
          this.montantEstime = montantEstime;
          this.dateEmission = dateEmission;
          this.dateValidite = dateValidite;
          this.accepte = accepte;
          this.IdProjet = IdProjet;
    }

    public double getMontantEstime() {
        return montantEstime;
    }
    public void setMontantEstime(double montantEstime) {
        this.montantEstime = montantEstime;
    }
    public String getDateEmission() {
        return dateEmission.toString();
    }
    public void setDateEmission(Date dateEmission) {
        this.dateEmission = dateEmission;
    }
    public String getDateValidite() {
        return dateValidite.toString();
    }
    public void setDateValidite(Date dateValidite) {
        this.dateValidite = dateValidite;
    }
    public boolean isAccepte() {
        return accepte;
    }
    public void setAccepte(boolean accepte) {
        this.accepte = accepte;
    }
    public int getIdProjet() {
        return IdProjet;
    }
    public void setIdProjet(int IdProjet) {
        this.IdProjet = IdProjet;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
