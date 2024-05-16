library(ggplot2)

# Beispiel-Daten (ersetze dies durch deine eigenen Daten)
line <- data.frame(
  Jahr = c(2000, 2001, 2002, 2003, 2004),  # Jahre
  Schluss = c(0.92, 0.95, 0.98, 1.02, 1.05)  # Euro-Dollar-Wechselkurse
)

# Liniendiagramm mit ggplot2 erstellen
ggplot(line, aes(x = Jahr, y = Schluss)) +
  geom_line() +
  labs(x = "Jahr", y = "€/$-Kurs",
       title = "Wechselkurs je Jahr")