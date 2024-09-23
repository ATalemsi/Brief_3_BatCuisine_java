package main.java.com.baticuisine.Presentation;

import main.java.com.baticuisine.controller.ClientController;
import main.java.com.baticuisine.controller.ComposantController;
import main.java.com.baticuisine.controller.DevisController;
import main.java.com.baticuisine.controller.ProjetController;
import main.java.com.baticuisine.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestionProjetsCLI {

    private static final Logger logger = LoggerFactory.getLogger(GestionProjetsCLI.class);
    private final Connection connection;
    private final ClientController clientController;
    private final ProjetController projetController;
    private final ComposantController composantController;
    private final DevisController devisController;

    public GestionProjetsCLI(Connection connection) {
        this.connection = connection;
        this.clientController = new ClientController(connection);
        this.projetController = new ProjetController(connection);
        this.composantController = new ComposantController(connection);
        this.devisController = new DevisController(connection);
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                logger.info("=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
                logger.info("=== Menu Principal ===");
                logger.info("1. Créer un nouveau projet");
                logger.info("2. Afficher les projets existants");
                logger.info("3. Calculer le coût d'un projet");
                logger.info("4. Quitter");
                logger.info("Choisissez une option : ");
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        // Create a new project
                        logger.info("--- Création d'un Nouveau Projet ---");
                        logger.info("Entrez le nom du projet : ");
                        String nomProjet = scanner.nextLine();
                        logger.info("Entrez Adresse du chantier : ");
                        String adresse_chantier = scanner.nextLine();
                        logger.info("Entrez la surface de la cuisine (en m²) : ");
                        double surface = scanner.nextDouble();
                        scanner.nextLine();  // Consume newline


                        logger.info("--- Recherche de client ---");
                        logger.info("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
                        logger.info("1. Chercher un client existant");
                        logger.info("2. Ajouter un nouveau client");
                        logger.info("Choisissez une option : ");
                        int clientOption = scanner.nextInt();
                        scanner.nextLine();

                        Client client = null;
                        if (clientOption == 1) {
                            logger.info("Entrez le nom du client : ");
                            String nomClient = scanner.nextLine();
                            client = clientController.rechercherParNom(nomClient);
                            if (client == null) {
                                logger.warn("Client non trouvé.");
                                continue;
                            }
                        } else if (clientOption == 2) {
                            logger.info("Entrez le nom du client : ");
                            String nomClient = scanner.nextLine();
                            logger.info("Entrez le prenom du client : ");
                            String prenomClient = scanner.nextLine();
                            logger.info("Entrez l'adresse du client : ");
                            String adresse = scanner.nextLine();
                            logger.info("Entrez le numéro de téléphone du client : ");
                            String telephone = scanner.nextLine();
                            logger.info("Client est-il professionnel ? (true/false) : ");
                            boolean estProfessionnel = scanner.nextBoolean();
                            scanner.nextLine();
                            client = new Client(nomClient, prenomClient, adresse, telephone, estProfessionnel);
                            clientController.addClient(client);
                        }

                        logger.info("Client trouvé !");
                        logger.info("Nom : " + client.getNom());
                        logger.info("Adresse : " + client.getAdresse());
                        logger.info("Numéro de téléphone : " + client.getTelephone());
                        double margeBeneficiaire = 0;
                        double coutTotal = 0;
                        Projet.EtatProjet etatProjet = Projet.EtatProjet.EN_COURS;

                        Projet projet = new Projet(nomProjet, margeBeneficiaire, coutTotal, etatProjet, client.getId_client(), adresse_chantier);
                        int projectId = projetController.addProject(projet);

                        logger.info("Project created with ID: " + projectId);


                        // Add materials
                        logger.info("--- Ajout des matériaux ---");
                        while (true) {
                            logger.info("Entrez le nom du matériau : ");
                            String nomMateriel = scanner.nextLine();

                            logger.info("Entrez la quantité de ce matériau (en unités) : "); // Adding unit as 'unités'
                            double quantite = scanner.nextDouble();

                            logger.info("Entrez le coût unitaire de ce matériau (en MAD par unité) : "); // Unit: 'euros'
                            double coutUnitaire = scanner.nextDouble();

                            logger.info("Entrez le coût de transport de ce matériau (en MAD) : "); // Unit: 'euros'
                            double coutTransport = scanner.nextDouble();

                            logger.info("Entrez le coefficient de qualité du matériau (sans unité, par exemple (1,0)) : "); // No unit
                            double coefficientQualite = scanner.nextDouble();

                            logger.info("Entrez le taux de TVA du matériau (en pourcentage, par exemple 20 pour 20%) : "); // Unit: percentage
                            double tauxTva = scanner.nextDouble();


                            int idProjet = projectId;
                            scanner.nextLine();

                            Materiel materiel = new Materiel(nomMateriel, tauxTva, coutUnitaire, quantite, coutTransport, coefficientQualite, idProjet);
                            composantController.addComposant(materiel);  // Controller interacts with DAO

                            logger.info("Matériau ajouté avec succès !");
                            logger.info("Voulez-vous ajouter un autre matériau ? (y/n) : ");
                            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                                break;
                            }
                        }

                        // Add labor (Main-d'œuvre)
                        logger.info("--- Ajout de la main-d'œuvre ---");
                        while (true) {
                            logger.info("Entrez le nom de la main-d'œuvre : ");
                            String nomMainDoeuvre = scanner.nextLine();

                            logger.info("Entrez le taux horaire de cette main-d'œuvre (en MAD par heure) : "); // Unit: 'euros/hour'
                            double tauxHoraire = scanner.nextDouble();

                            logger.info("Entrez le nombre d'heures travaillées (en heures) : "); // Unit: 'hours'
                            double heuresTravail = scanner.nextDouble();

                            logger.info("Entrez le taux de TVA de la main-d'œuvre (en pourcentage, par exemple 20 pour 20%) : "); // Unit: percentage
                            double tauxTva = scanner.nextDouble();

                            logger.info("Entrez la productivité de l'ouvrier (sans unité, par exemple (0.8)) : "); // No unit
                            double productiviteOuvrier = scanner.nextDouble();

                            int idProjet = projectId;

                            scanner.nextLine();  // Consume newline

                            // Create and add MainOeuvre object
                            MainOeuvre mainDoeuvre = new MainOeuvre(nomMainDoeuvre, tauxTva, tauxHoraire, heuresTravail, productiviteOuvrier, idProjet);
                            composantController.addComposant(mainDoeuvre);

                            logger.info("Main-d'œuvre ajoutée avec succès !");
                            logger.info("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
                            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                                break;
                            }
                        }
                        List<Composant> composants = composantController.getAllComposants();
                        logger.info("--- Calcul du coût total ---");
                        logger.info("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
                        boolean appliquerTva = scanner.nextLine().equalsIgnoreCase("y");
                        double tva = 0.2;
                        if (appliquerTva) {
                            logger.info("Entrez le pourcentage de TVA (%) : ");
                            tva = scanner.nextDouble();
                            scanner.nextLine();
                        }

                        logger.info("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
                        boolean appliquerMarge = scanner.nextLine().equalsIgnoreCase("y");
                        double marge = 0;
                        if (appliquerMarge) {
                            logger.info("Entrez le pourcentage de marge bénéficiaire (%) : ");
                            marge = scanner.nextDouble();
                            scanner.nextLine();
                        }

                        double coutTotalProject = projetController.calculerCoutTotal(projet,projectId, composants, tva, marge);
                        logger.info("Coût total du projet : " + coutTotalProject + " €");

                        // Save estimate
                        logger.info("--- Enregistrement du Devis ---");
                        logger.info("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
                        String dateEmissionStr = scanner.nextLine();
                        logger.info("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
                        String dateValiditeStr = scanner.nextLine();

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date dateEmission = null;
                        Date dateValidite = null;
                        try {
                            dateEmission = sdf.parse(dateEmissionStr);
                            dateValidite = sdf.parse(dateValiditeStr);
                        } catch (ParseException e) {
                            logger.info("Erreur de format de date. Veuillez entrer une date au format jj/mm/aaaa.");
                            break;
                        }
                        double montantEstime = projetController.calculerCoutTotal(projet,projectId,composants,tva, marge);
                        boolean accepte = false;

                        Devis devis = new Devis(montantEstime, dateEmission, dateValidite, accepte, projectId);
                        devisController.addDevis(devis);
                        logger.info("Devis enregistré avec succès !");

                        break;

                    case 2:
                        // Display existing projects
                        logger.info("--- Affichage des Projets Existants ---");
                        projetController.getAllProjects();
                        break;
                    case 3:
                        // Calculate project cost
                        logger.info("Fonctionnalité non disponible pour le moment.");
                        break;

                    case 4:
                        logger.info("Merci d'avoir utilisé notre application !");
                        System.exit(0);
                        break;

                    default:
                        logger.warn("Option invalide, veuillez réessayer.");
                }
            }
        } catch (Exception e) {
            logger.error("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
}
