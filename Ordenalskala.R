#Ordenalskala

# Beispiel-Daten
umwelt_data <- data.frame(
  Item1 = c(4, 3, 5),
  Item2 = c(2, 4, 3),
  Item3 = c(3, 5, 4)
)

# Berechnung des Mittelwerts für jede Zeile (Teilnehmer)
umwelt_data$Umwelt_skala <- rowMeans(umwelt_data, na.rm = TRUE)

# Umwandlung in einen ordinalen Faktor
umwelt_data$Umwelt_skala <- factor(
  umwelt_data$Umwelt_skala,
  levels = c("Gar nicht wichtig", "Weniger wichtig", "Neutral", "Wichtig", "Sehr wichtig")
)

# Balkendiagramm mit Mittelwertslinie
library(ggplot2)
ggplot(umwelt_data, aes(x = Umwelt_skala)) +
  geom_bar() +
  geom_hline(yintercept = mean(umwelt_data$Umwelt_skala), color = "red", linetype = "dashed") +
  labs(x = "Umweltbewusstsein", y = "Anzahl der Teilnehmer", title = "Umweltbewusstseinsskala mit Mittelwertslinie")



# Beispiel-Daten
umwelt_data <- data.frame(
  Item1 = c(4, 3, 5),
  Item2 = c(2, 4, 3),
  Item3 = c(3, 5, 4)
)

# Berechnung des Mittelwerts für jede Zeile (Teilnehmer)
umwelt_data$Umwelt_skala <- rowMeans(umwelt_data, na.rm = TRUE)

# Umwandlung in einen ordinalen Faktor
umwelt_data$Umwelt_skala <- factor(
  umwelt_data$Umwelt_skala,
  levels = c("Gar nicht wichtig", "Weniger wichtig", "Neutral", "Wichtig", "Sehr wichtig")
)

# Balkendiagramm mit Mittelwertslinie
library(ggplot2)
ggplot(umwelt_data, aes(x = Umwelt_skala)) +
  geom_bar() +
  geom_hline(yintercept = mean(umwelt_data$Umwelt_skala), color = "red", linetype = "dashed") +
  labs(x = "Umweltbewusstsein", y = "Anzahl der Teilnehmer", title = "Umweltbewusstseinsskala mit Mittelwertslinie")