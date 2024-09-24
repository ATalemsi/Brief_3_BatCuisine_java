package main.java.com.baticuisine.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validation {

    public static String validateStringInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }
        return input.trim();
    }

    public static String validateStringIsNom(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Le champ ne peut pas être vide.");
        }

        // Regular expression to match alphabetic characters and spaces
        if (!input.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            throw new IllegalArgumentException("Le nom ne peut contenir que des lettres.");
        }

        return input.trim();
    }

    public static double validateDoubleInput(String input) {
        try {
            double value = Double.parseDouble(input);
            if (value < 0) {
                throw new IllegalArgumentException("Value cannot be negative.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format.");
        }
    }

    public static boolean validateBooleanInput(String input) {
        if (!input.equalsIgnoreCase("true") && !input.equalsIgnoreCase("false")) {
            throw new IllegalArgumentException("Input must be 'true' or 'false'.");
        }
        return Boolean.parseBoolean(input);
    }

    public static LocalDate validateDateInput(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date must be in the format dd/MM/yyyy.");
        }
    }

    public static int validateIntegerInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valeur non valide. Veuillez entrer un nombre entier.");
        }
    }

    public static double validatePercentageInput(String input) {
        try {
            double percentage = Double.parseDouble(input);
            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException("Le pourcentage doit être compris entre 0 et 100.");
            }
            return percentage;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valeur non valide. Veuillez entrer un pourcentage valide.");
        }
    }

    // Below are the methods to continuously prompt the user for input

    public static String getValidatedStringInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return validateStringInput(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String getValidatedStringNom(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return validateStringIsNom(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static double getValidatedDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return validateDoubleInput(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean getValidatedBooleanInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return validateBooleanInput(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static LocalDate getValidatedDateInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return validateDateInput(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int getValidatedIntegerInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return validateIntegerInput(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static double getValidatedPercentageInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return validatePercentageInput(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
