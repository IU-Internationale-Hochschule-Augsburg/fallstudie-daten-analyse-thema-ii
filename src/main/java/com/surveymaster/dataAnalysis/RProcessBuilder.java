package com.surveymaster.dataAnalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RProcessBuilder {
    public static void main(String[] args) {
        try {
            // Pfad zur R-Installation anpassen
            String rScriptPath = "/Library/Frameworks/R.framework/Versions/Current/Resources/bin/Rscript";
            String rFilePath = "/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/Algorithm.R"; // Diesen Pfad zur R-Datei anpassen

            // Befehl zum Ausführen des R-Skripts
            String command = rScriptPath + " " + rFilePath;

            // Starte den R-Prozess
            Process process = new ProcessBuilder(command).start();

            // Lese die Ausgabe des R-Prozesses
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Warte auf den Abschluss des R-Prozesses
            int exitCode = process.waitFor();
            System.out.println("R-Prozess beendet mit Exit-Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
