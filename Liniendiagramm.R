library(ggplot2)

# Beispiel-Daten (ersetze dies durch deine eigenen Daten)
line <- data.frame(
  Jahr <- c(2000, 2001, 2002, 2003, 2004),  # Jahre
  Schluss <- c(0.92, 0.95, 0.98, 1.02, 1.05)  # Euro-Dollar-Wechselkurse
)

# Liniendiagramm mit ggplot2 erstellen
ggplot(line, aes(x = Jahr, y = Schluss)) +
  geom_line(color = "blue", linewidth = 1) + #Linie wird blau und dick gemacht um sie deutlicher zu machen
  geom_point(color = "red", size = 3) + #Punkte werden hinzugefügt und rot eingefärbt, um die Datenpunkte hervorzuheben.
  labs(x = "Jahr", y = "€/$-Kurs",
       title = "Wechselkurs je Jahr") +
  theme_minimal() +
  theme(
    plot.title = element_text(hjust = 0.5),
    axis.text = element_text(size = 12),
    axis.title = element_text(size = 14)
  )

