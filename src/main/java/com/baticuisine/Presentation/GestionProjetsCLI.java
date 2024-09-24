package main.java.com.baticuisine.Presentation;

import main.java.com.baticuisine.controller.ClientController;
import main.java.com.baticuisine.controller.ComposantController;
import main.java.com.baticuisine.controller.DevisController;
import main.java.com.baticuisine.controller.ProjetController;
import main.java.com.baticuisine.model.*;

import main.java.com.baticuisine.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GestionProjetsCLI {

    private static final Logger logger = LoggerFactory.getLogger(GestionProjetsCLI.class);
    private final Connection connection;
    private final ClientController clientController;
    private final ProjetController projetController;
    private final ComposantController composantController;
    private final DevisController devisController;
    private Scanner scanner;

    public GestionProjetsCLI(Connection connection) {
        this.connection = connection;
        this.clientController = new ClientController(connection);
        this.projetController = new ProjetController(connection);
        this.composantController = new ComposantController(connection);
        this.devisController = new DevisController(connection);
        this.scanner = new Scanner(System.in);
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
                        String nomProjet = Validation.getValidatedStringNom(scanner, "Entrez le nom du projet : ");
                        String adresse_chantier = Validation.getValidatedStringInput(scanner, "Entrez l'adresse du chantier : ");
                        double surface = Validation.getValidatedDoubleInput(scanner, "Entrez la surface de la cuisine (en m²) : ");


                        logger.info("--- Recherche de client ---");
                        int clientOption = Validation.getValidatedIntegerInput(scanner,
                                "Souhaitez-vous chercher un client existant ou en ajouter un nouveau ??\n1. Chercher un client existant\n2. Ajouter un nouveau client\nChoisissez une option : ");

                        Client client = null;
                        if (clientOption == 1) {
                            String nomClient = Validation.getValidatedStringInput(scanner, "Entrez le nom du client existant : ");
                            Optional<Client> optionalClient = clientController.rechercherParNom(nomClient);
                            if (optionalClient.isPresent()) {
                                client = optionalClient.get(); // Safely get the Client object if present
                            } else {
                                logger.warn("Client non trouvé.");
                                continue;
                            }
                        } else if (clientOption == 2) {
                            String nomClient = Validation.getValidatedStringNom(scanner, "Entrez le nom du nouveau client : ");
                            String prenomClient = Validation.getValidatedStringInput(scanner, "Entrez le prénom du client : ");
                            String adresseClient = Validation.getValidatedStringInput(scanner, "Entrez l'adresse du client : ");
                            String telephoneClient = Validation.getValidatedStringInput(scanner, "Entrez le numéro de téléphone du client : ");
                            boolean estProfessionnel = Validation.getValidatedBooleanInput(scanner, "Le client est-il professionnel ? (true/false) : ");

                            client = new Client(nomClient, prenomClient, adresseClient, telephoneClient, estProfessionnel);
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

                        logger.info("--- Ajout des matériaux ---");
                        while (true) {
                            String nomMateriel = Validation.getValidatedStringInput(scanner, "Entrez le nom du matériau : ");

                            double quantite = Validation.getValidatedDoubleInput(scanner, "Entrez la quantité de ce matériau  (en unités) : ");
                            ;

                            double coutUnitaire = Validation.getValidatedDoubleInput(scanner, "Entrez le coût unitaire de ce matériau (en MAD par unité) : ");
                            ;

                            double coutTransport = Validation.getValidatedDoubleInput(scanner, "Coût de transport (en MAD) : ");
                            ;

                            double coefficientQualite = Validation.getValidatedDoubleInput(scanner, "Entrez le coefficient de qualité du matériau (sans unité, par exemple (1,0)) : ");


                            double tauxTva = Validation.getValidatedPercentageInput(scanner, "Entrez le taux de TVA du matériau (en pourcentage, par exemple 20 pour 20%)  : ");
                            ;

                            int idProjet = projectId;

                            Materiel materiel = new Materiel(nomMateriel, tauxTva, coutUnitaire, quantite, coutTransport, coefficientQualite, idProjet);
                            composantController.addComposant(materiel);

                            logger.info("Matériau ajouté avec succès !");
                            logger.info("Voulez-vous ajouter un autre matériau ? (y/n) : ");
                            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                                break;
                            }
                        }

                        // Add labor (Main-d'œuvre)
                        logger.info("--- Ajout de la main-d'œuvre ---");
                        while (true) {

                            String nomMainDoeuvre = Validation.getValidatedStringInput(scanner, "Entrez le nom de la main-d'œuvre : ");
                            ;

                            logger.info("Entrez le taux horaire de cette main-d'œuvre (en MAD par heure) : "); // Unit: 'euros/hour'
                            double tauxHoraire = Validation.getValidatedDoubleInput(scanner, "Entrez le taux horaire de cette main-d'œuvre (en MAD par heure) : ");
                            ;

                            double heuresTravail = Validation.getValidatedDoubleInput(scanner, "Entrez le nombre d'heures travaillées (en heures) :  ");
                            ;

                            double tauxTva = Validation.getValidatedPercentageInput(scanner, "Entrez le taux de TVA de la main-d'œuvre (en pourcentage, par exemple 20 pour 20%) : ");
                            ;

                            double productiviteOuvrier = Validation.getValidatedDoubleInput(scanner, "Entrez la productivité de l'ouvrier (sans unité, par exemple (0.8)) : ");

                            int idProjet = projectId;

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
                            tva = Validation.getValidatedPercentageInput(scanner, "Entrez le pourcentage de TVA (%) :");
                        }

                        logger.info("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
                        boolean appliquerMarge = scanner.nextLine().equalsIgnoreCase("y");
                        double marge = 0;
                        if (appliquerMarge) {
                            marge = Validation.getValidatedPercentageInput(scanner, "Entrez le pourcentage de marge bénéficiaire (%) :");

                        }

                        double coutTotalProject = projetController.calculerCoutTotal(projet, projectId, composants, tva, marge, client);
                        logger.info("Coût total du projet : " + coutTotalProject + " €");


                        logger.info("--- Enregistrement du Devis ---");
                        LocalDate dateEmission = Validation.getValidatedDateInput(scanner, "Entrez la date d'émission du devis (format : jj/mm/aaaa) :  ");
                        ;
                        LocalDate dateValidite = Validation.getValidatedDateInput(scanner, "Entrez la date de validité du devis (format : jj/mm/aaaa) : ");

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        logger.info("Date d'émission : " + dateEmission.format(formatter));
                        logger.info("Date de validité : " + dateValidite.format(formatter));
                        double montantEstime = projetController.calculerCoutTotal(projet, projectId, composants, tva, marge, client);
                        boolean accepte = false;


                        Devis devis = new Devis(montantEstime, dateEmission, dateValidite, accepte, projectId);
                        devisController.addDevis(devis);
                        logger.info("Devis enregistré avec succès !");
                        break;
                    case 2:
                        logger.info("--- Affichage des Projets Existants ---");
                        projetController.getAllProjects();
                        break;
                    case 3:
                        logger.info("--- Calcul du coût d'un projet ---");

                        // Step 1: Display all projects with their IDs and names
                        List<Projet> allProjects = projetController.getProjets();
                        if (allProjects.isEmpty()) {
                            logger.info("Aucun projet disponible.");
                            break;
                        }
                        logger.info("Projets disponibles : ");
                        for (Projet p : allProjects) {
                            logger.info("ID: " + p.getId() + ", Nom: " + p.getNomProjet());
                        }
                        int IdProjet = Validation.getValidatedIntegerInput(scanner, "Entrez l'ID du projet pour lequel vous voulez calculer le coût :");

                        Projet projectById = projetController.getProjectById(IdProjet);
                        double Total = projectById.getCoutTotal();
                        logger.info("Coût total du projet : " + Total + " MAD");

                        logger.info("Voulez-vous voir le devis pour ce projet ? (y/n) : ");
                        boolean viewDevis = scanner.nextLine().equalsIgnoreCase("y");
                        if (viewDevis) {
                            Devis devisById = devisController.getDevisByProjectId(IdProjet);
                            if (devisById == null) {
                                logger.warn("Aucun devis trouvé pour ce projet. Vous devez créer un devis.");
                                logger.info("Voulez-vous créer un nouveau devis pour ce projet ? (y/n) : ");
                                boolean createDevis = scanner.nextLine().equalsIgnoreCase("y");
                                if (createDevis) {
                                    LocalDate dateEmissionNew = null;
                                    LocalDate dateValiditeNew = null;
                                    double montantEstimeNew;
                                    boolean accepteNew = false;

                                    logger.info("--- Enregistrement du Devis ---");
                                    dateEmissionNew = Validation.getValidatedDateInput(scanner, "Entrez la date d'émission du devis (format : jj/mm/aaaa) :  ");
                                    ; // Validate date input
                                    dateValiditeNew = Validation.getValidatedDateInput(scanner, "Entrez la date de validité du devis (format : jj/mm/aaaa) : ");  // Validate date input

                                    logger.info("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
                                    boolean appliquerMargeNew = scanner.nextLine().equalsIgnoreCase("y");
                                    double margeNew = 0;
                                    if (appliquerMargeNew) {
                                        margeNew = Validation.getValidatedPercentageInput(scanner, "Entrez le pourcentage de marge bénéficiaire (%) :"); // Validate percentage (0-100)
                                        logger.info("Marge bénéficiaire : " + margeNew + "%");
                                    }
                                    logger.info("List de Client : ");
                                    Client clientNewDevis = getClient();
                                    montantEstimeNew = projetController.calculerCoutTotal(projectById, IdProjet,
                                            composantController.getAllComposants(), 0.2, margeNew, clientNewDevis);

                                    Devis devisNew = new Devis(montantEstimeNew, dateEmissionNew, dateValiditeNew, accepteNew, IdProjet);
                                    devisController.addDevis(devisNew);
                                    logger.info("Devis enregistré avec succès !");
                                }
                            } else {
                                logger.info("Devis trouvé pour le projet.");
                                logger.info("Montant estimé : " + devisById.getMontantEstime());
                                logger.info("Date d'émission : " + devisById.getDateEmission());
                                logger.info("Date de validité : " + devisById.getDateValidite());
                                logger.info("Accepté : " + (devisById.isAccepte() ? "Oui" : "Non"));


                                if (!devisById.isAccepte()) {
                                    logger.info("Voulez-vous accepter le devis ? (y/n) : ");
                                    boolean acceptDevis = scanner.nextLine().equalsIgnoreCase("y");
                                    if (acceptDevis) {
                                        devisController.acceptDevis(devisById.getId());
                                        logger.info("Le devis a été accepté !");

                                        projetController.updateProjectStatus(IdProjet, Projet.EtatProjet.TERMINE);
                                        logger.info("Le statut du projet a été mis à jour en 'TERMINE'.");
                                    } else {
                                        projetController.updateProjectStatus(IdProjet, Projet.EtatProjet.ANNULE);
                                        logger.info("Le devis n'a pas été accepté. Le statut du projet a été mis à jour en 'ANNULE'.");
                                    }
                                } else {
                                    logger.info("Le devis a déjà été accepté.");
                                }
                            }
                        }
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

    private Client getClient() {
        List<Client> clients = clientController.getAllClients();
        if (clients.isEmpty()) {
            logger.info("Aucun client trouvé.");
            return null;
        }

        logger.info("Clients disponibles : ");
        clients.forEach(c -> logger.info("ID: " + c.getId_client() + ", Nom: " + c.getNom()));
        logger.info("Entrez l'ID du client : ");
        int clientId = Validation.validateIntegerInput(scanner.nextLine());
        scanner.nextLine();

        return clients.stream()
                .filter(c -> c.getId_client() == clientId)
                .findFirst()
                .orElseGet(() -> {
                    logger.info("Client non trouvé. Création d'un nouveau client.");
                    return null;
                });
    }
}
