# Beispiel-Daten für Temperatur in Grad Celsius
temperature <- c(10, 20, 30, 25, 15)

# Aussage: "Heute ist es wärmer als gestern"
# Spezifische Äußerung: "Dieses Jahr an Weihnachten ist es doppelt so kalt wie letztes Jahr"

# Grafische Darstellung der Temperaturdaten
plot(temperature, type = "o", pch = 16, col = "blue",
     xlab = "Tag", ylab = "Temperatur (°C)",
     main = "Temperaturverlauf")

# Intervallskala: Unterschiede zwischen den Werten sind quantifizierbar

# Beispiel-Daten für IQ-Testergebnisse
iq_scores <- c(100, 120, 90, 110, 130)

# Grafische Darstellung der Intervallskala
hist(iq_scores, main = "Verteilung der IQ-Testergebnisse",
     xlab = "IQ-Score", ylab = "Anzahl",
     col = "purple", border = "white")

# Intervallskala: Reihenfolge und Abstände sind relevant

# Beispiel-Daten für Einstellungsmessungen
attitude_scores <- c(5, 4, 3, 2, 4)

# Grafische Darstellung der Intervallskala
hist(attitude_scores, main = "Verteilung der Einstellungsmessungen",
     xlab = "Bewertung", ylab = "Anzahl",
     col = "skyblue", border = "pink")

# Intervallskala: Bewertungen können quantifiziert werden


