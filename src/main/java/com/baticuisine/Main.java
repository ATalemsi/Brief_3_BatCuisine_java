package main.java.com.baticuisine;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;
import main.java.com.baticuisine.Presentation.GestionProjetsCLI;
import main.java.com.baticuisine.db.DatabaseConnection;

import java.io.File;
import java.sql.Connection;
public class Main {
    public static void main(String[] args) {
        configureLogback();
        try {
            GestionProjetsCLI gestionProjetsCLI = new GestionProjetsCLI(DatabaseConnection.getConnection());
            gestionProjetsCLI.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void configureLogback() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try {

            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);


            context.reset();
            String logbackConfigFilePath = "src/main/resources/logback.xml";
            configurator.doConfigure(new File(logbackConfigFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }


        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }
}