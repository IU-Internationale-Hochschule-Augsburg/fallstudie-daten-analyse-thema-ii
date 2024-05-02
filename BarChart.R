#barplot(data_xls$Alter)

# Beispiel-Daten: Alter von Personen
alter <- c(25, 30, 22, 40, 28, 35, 29)

# Berechne den Durchschnitt des Alters
durchschnitt_alter <- mean(alter)

# Erstelle ein Balkendiagramm mit dem Durchschnitt als Linie
barplot(alter, names.arg = c("P1", "P2", "P3", "P4", "P5", "P6", "P7"),
        main = "Alter der Personen",
        xlab = "Personen", ylab = "Alter",
        col = "skyblue")
abline(h = durchschnitt_alter, col = "red", lwd = 2)
