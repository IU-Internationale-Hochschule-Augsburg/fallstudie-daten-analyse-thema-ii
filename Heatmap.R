# Notwendige Pakete
library(reshape2)
library(ggplot2)

# Beispiel-Daten mit ID-Variablen
data <- data.frame(
  ID = 1:5,
  Person = c("Ann", "Bob", "Tom", "Joy", "Sue"),
  Arm = rnorm(5, 50, 20),
  Leg = rnorm(5, 50, 20),
  Chest = rnorm(5, 50, 20),
  Gut = rnorm(5, 50, 20),
  Head = rnorm(5, 50, 20)
)

# Daten umstrukturieren
data_melt <- melt(data, id.vars = c("ID", "Person"))

# Heatmap mit ggplot2 erstellen
heatmap_plot <- ggplot(data_melt, aes(x = variable, y = Person, fill = value)) +
  geom_tile() +
  scale_fill_gradient(low = "white", high = "red") +
  labs(x = "Körperteil", y = "Person", title = "Heatmap der Körperteile nach Personen") +
  theme_minimal()

# Plot anzeigen
print(heatmap_plot)

# Plot speichern (Zum speichern der aktuellen Graphik, kann optional auch deaktiviert werden)
ggsave(filename = "~/Fallstudie/heatmap_plot.png", plot = heatmap_plot)
