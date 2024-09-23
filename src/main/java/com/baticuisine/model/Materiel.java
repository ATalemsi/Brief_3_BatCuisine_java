package main.java.com.baticuisine.model;

public class Materiel extends Composant {
    private double coutUnitaire;
    private double quantite;
    private double coutTransport;
    private double coefficientQualite;

    public Materiel(String nom, double tauxTva, double coutUnitaire, double quantite, double coutTransport, double coefficientQualite, int idProjet) {
        super(nom, "Materiel", tauxTva, idProjet);
        this.coutUnitaire = coutUnitaire;
        this.quantite = quantite;
        this.coutTransport = coutTransport;
        this.coefficientQualite = coefficientQualite;
    }

    // Getters and setters
    public double getCoutUnitaire() {
        return coutUnitaire;
    }

    public void setCoutUnitaire(double coutUnitaire) {
        if (coutUnitaire < 0) {
            throw new IllegalArgumentException("Le coût unitaire ne peut pas être négatif");
        }
        this.coutUnitaire = coutUnitaire;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        if (quantite < 0) {
            throw new IllegalArgumentException("La quantité ne peut pas être négative");
        }
        this.quantite = quantite;
    }

    public double getCoutTransport() {
        return coutTransport;
    }

    public void setCoutTransport(double coutTransport) {
        if (coutTransport < 0) {
            throw new IllegalArgumentException("Le coût de transport ne peut pas être négatif");
        }
        this.coutTransport = coutTransport;
    }

    public double getCoefficientQualite() {
        return coefficientQualite;
    }

    public void setCoefficientQualite(double coefficientQualite) {
        if (coefficientQualite <= 0) {
            throw new IllegalArgumentException("Le coefficient de qualité ne peut pas être nul ou négatif");
        }
        this.coefficientQualite = coefficientQualite;
    }

    @Override
    public double calculerCout() {
        return (coutUnitaire * quantite * coefficientQualite) + coutTransport;
    }

    @Override
    public String toString() {
        return "Materiel{" +
                "nom=" + getNom() +
                ", coutUnitaire=" + coutUnitaire +
                ", quantite=" + quantite +
                ", coutTransport=" + coutTransport +
                ", coefficientQualite=" + coefficientQualite +
                ", idProjet=" + getIdProjet() +
                '}';
    }



}
