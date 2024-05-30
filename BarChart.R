# Beispiel-Daten: Alter von Personen
alter <- c(25, 30, 22, 40, 28, 35, 29)

# Berechne den Durchschnitt des Alters
durchschnitt_alter <- mean(alter)

# Variablen für Achsenbeschriftungen
x_labels <- c("P1", "P2", "P3", "P4", "P5", "P6", "P7")
title <- "Alter der Personen"
x_label <- "Personen"
y_label <- "Alter"

# Hier der Text für die Caption
caption <- "Die rote Linie kennzeichnet den Durchschnittwert"

# Erstelle ein Balkendiagramm mit dem Durchschnitt als Linie
barplot(alter, names.arg = x_labels,
        main = title,
        xlab = x_label, ylab = y_label,
        col = "skyblue")
abline(h = durchschnitt_alter, col = "red", lwd = 2)

# 1.Zeile ist der Code für die Caption (Kursiver Text unter der Graphik)
mtext(text = bquote(italic(.(caption))), side = 1, line = 4, cex = 0.8)
