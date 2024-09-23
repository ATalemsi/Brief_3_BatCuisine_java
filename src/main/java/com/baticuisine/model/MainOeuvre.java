package main.java.com.baticuisine.model;

public class MainOeuvre extends Composant {
    private double tauxHoraire;
    private double heuresTravail;
    private double productiviteOuvrier;

    public MainOeuvre(String nom, double tauxTva, double tauxHoraire, double heuresTravail, double productiviteOuvrier, int idProjet) {
        super(nom, "Main-œuvre", tauxTva, idProjet);
        setTauxHoraire(tauxHoraire);
        setHeuresTravail(heuresTravail);
        setProductiviteOuvrier(productiviteOuvrier);
    }

    // Getters and setters
    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
        if (tauxHoraire < 0) {
            throw new IllegalArgumentException("Taux horaire ne peut pas être négatif");
        }
        this.tauxHoraire = tauxHoraire;
    }

    public double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(double heuresTravail) {
        if (heuresTravail < 0) {
            throw new IllegalArgumentException("Les heures de travail ne peuvent pas être négatives");
        }
        this.heuresTravail = heuresTravail;
    }

    public double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        if (productiviteOuvrier <= 0) {
            throw new IllegalArgumentException("La productivité ne peut pas être nulle ou négative");
        }
        this.productiviteOuvrier = productiviteOuvrier;
    }

    @Override
    public double calculerCout() {
        return tauxHoraire * heuresTravail * productiviteOuvrier;
    }

    @Override
    public String toString() {
        return "MainOeuvre{" +
                "nom=" + getNom() +
                ", tauxHoraire=" + tauxHoraire +
                ", heuresTravail=" + heuresTravail +
                ", productiviteOuvrier=" + productiviteOuvrier +
                ", idProjet=" + getIdProjet() +
                '}';
    }

}
