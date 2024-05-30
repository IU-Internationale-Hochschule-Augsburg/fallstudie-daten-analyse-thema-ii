# Beispiel-Daten (ersetze dies durch deine eigenen Daten)
df <- data.frame(
  Alter <- c(30, 35, 40, 45, 50),  # Altersgruppen
  Anzahl <- c(5, 8, 12, 7, 10)     # Häufigkeiten
)

# Balkendiagramm erstellen
barplot(df$Anzahl, names.arg = df$Alter, horiz = TRUE,
        main = "Häufigkeit der Altersgruppen",
        xlab = "Anzahl", ylab = "Alter")

