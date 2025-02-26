package com.surveymaster.dataAnalysis;

/*import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AnalysisAlgorithm {

    public static void answersChart(long survey_id) {
        // Create an RCaller instance
        RCaller caller = RCaller.create();
        // Create an RCode instance
        RCode code = RCode.create();

        // Load the R script from the resources folder
        String scriptPath = "/R/Algorithm.R"; // Note the leading slash

        // Read the R script content
        StringBuilder rScript = new StringBuilder();
        try (InputStream inputStream = AnalysisAlgorithm.class.getResourceAsStream(scriptPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rScript.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return; // Handle the error appropriately
        }

        // Load and run the R script from the content
        code.clear();
        code.addRCode(rScript.toString());
        code.addRCode("algo_answers(" + survey_id + ")");

        // Set the RCode instance to RCaller
        caller.setRCode(code);

        // Run the R script
        caller.runOnly();



    }

    public static void compareAnswersChart(long question_id_1, long question_id_2) {
        // Create an RCaller instance
        RCaller caller = RCaller.create();
        // Create an RCode instance
        RCode code = RCode.create();

        // Load the R script from the resources folder
        String scriptPath = "/R/AlgoCompareAnswers.R"; // Note the leading slash

        // Read the R script content
        StringBuilder rScript = new StringBuilder();
        try (InputStream inputStream = AnalysisAlgorithm.class.getResourceAsStream(scriptPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rScript.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return; // Handle the error appropriately
        }

        // Load and run the R script from the content
        code.clear();
        code.addRCode(rScript.toString());
        code.addRCode("algo_compare_answers(" + question_id_1 + "," + question_id_2 + ")");

        // Set the RCode instance to RCaller
        caller.setRCode(code);

        // Run the R script
        caller.runOnly();
    }
}

 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AnalysisAlgorithm {

    /**
     * Führt das R-Skript "Algorithm.R" aus und übergibt die Survey-ID als Parameter.
     * Erstellt Diagramme basierend auf den Antworten der einzelnen Fragen einer Umfrage.
     *
     * @param survey_id Die ID der Umfrage, für die Diagramme erstellt werden sollen.
     */
    public static void answersChart(long survey_id) {
        try {
            // R-Skript-Pfad (überprüfen, ob der Pfad korrekt ist!)
            String rScriptPath = "/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/Algorithm.R";
            System.out.println("🚀 Versuche, R-Skript zu starten: " + rScriptPath);
            System.out.println("📌 Survey ID: " + survey_id);

            // Erstelle den Prozess, um das R-Skript auszuführen
            ProcessBuilder processBuilder = new ProcessBuilder("Rscript", rScriptPath, String.valueOf(survey_id));

            // Prozess-Umgebung setzen, falls nötig
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Lese die Standard-Ausgabe des R-Skripts
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("🔹 R-Ausgabe:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Ausgabe in IntelliJ-Konsole anzeigen
            }

            // Warte, bis das R-Skript beendet ist
            int exitCode = process.waitFor();
            System.out.println("✅ R-Prozess beendet mit Exit-Code: " + exitCode);

            if (exitCode != 0) {
                System.err.println("❌ R-Skript hat möglicherweise einen Fehler!");
            }

        } catch (IOException e) {
            System.err.println("❌ Fehler beim Starten des R-Skripts: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("❌ Prozess wurde unterbrochen: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


    /**
     * Führt das R-Skript "AlgoCompareAnswers.R" aus und übergibt zwei Fragen-IDs.
     * Erstellt eine Heatmap, um die Antworten zweier Fragen zu vergleichen.
     *
     * @param question_id_1 Die erste Frage-ID.
     * @param question_id_2 Die zweite Frage-ID.
     */
    public static void compareAnswersChart(long question_id_1, long question_id_2) {
        try {
            // Pfad zum R-Skript (prüfen, ob dieser korrekt ist!)
            String rScriptPath = "/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/AlgoCompareAnswers.R";

            // Prozess für das R-Skript erstellen
            ProcessBuilder processBuilder = new ProcessBuilder("Rscript", rScriptPath,
                    String.valueOf(question_id_1), String.valueOf(question_id_2));

            // Starte den Prozess
            Process process = processBuilder.start();

            // Lese die Standard-Ausgabe des R-Prozesses
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("🔹 R-Ausgabe:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Lese die Fehler-Ausgabe des R-Prozesses (falls Fehler auftreten)
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println("❌ Fehler in R: " + line);
            }

            // Warte, bis das R-Skript beendet ist
            int exitCode = process.waitFor();
            System.out.println("✅ R-Prozess beendet mit Exit-Code: " + exitCode);

        } catch (IOException e) {
            System.err.println("❌ Fehler beim Starten des R-Skripts: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("❌ Prozess wurde unterbrochen: " + e.getMessage());
            Thread.currentThread().interrupt(); // Wiederherstellen des Interrupt-Status
        }
    }

    /**
     * Test-Methode zum Debuggen: Führt beide R-Skripte mit Test-IDs aus.
     */
    public static void main(String[] args) {
        System.out.println("🔍 Starte R-Test...");

        // Test: Diagramme für eine Umfrage erstellen
        System.out.println("\n🔹 Teste answersChart()...");
        answersChart(2); // Test mit einer Beispiel-Umfrage-ID

        // Test: Heatmap für zwei Fragen erstellen
        System.out.println("\n🔹 Teste compareAnswersChart()...");
        compareAnswersChart(2, 52);
    }
}
