package main.java.com.baticuisine.model;

import java.time.LocalDate;
import java.util.Date;

public class Devis {
    private int id;
    private double montantEstime;
    private LocalDate dateEmission;
    private LocalDate dateValidite;
    private boolean accepte;
    private int IdProjet;

    public Devis(double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte, int IdProjet) {
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
    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }
    public String getDateValidite() {
        return dateValidite.toString();
    }
    public void setDateValidite(LocalDate dateValidite) {
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
