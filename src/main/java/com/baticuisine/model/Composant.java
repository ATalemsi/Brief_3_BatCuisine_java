package main.java.com.baticuisine.model;

public abstract class Composant {
    private String nom;
    private String typeComposant;  // Can be 'Materiel' or 'MainDoeuvre'
    private double tauxTva;
    private int idProjet; // Foreign key to the Project

    public Composant(String nom, String typeComposant, double tauxTva, int idProjet) {
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.tauxTva = tauxTva;
        this.idProjet = idProjet;
    }

    // Getters and setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(String typeComposant) {
        this.typeComposant = typeComposant;
    }

    public double getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(double tauxTva) {
        this.tauxTva = tauxTva;
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    // Abstract method for calculating the total cost, to be implemented by subclasses
    public abstract double calculerCout();
}