# Beispiel-Daten: Alter von Personen
alter <- c(25, 30, 22, 40, 28, 35, 29)

# Berechne den Durchschnitt des Alters
durchschnitt_alter <- mean(alter)

# Variablen für Achsenbeschriftungen
x_labels <- c("P1", "P2", "P3", "P4", "P5", "P6", "P7")
title <- "Alter der Personen"
x_label <- "Personen"
y_label <- "Alter"

# Erstelle ein Balkendiagramm mit dem Durchschnitt als Linie
barplot(alter, names.arg = x_labels,
        main = title,
        xlab = x_label, ylab = y_label,
        col = "skyblue")
abline(h = durchschnitt_alter, col = "red", lwd = 2)
