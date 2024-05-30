#Nominalskala
# Beispiel-Daten (ersetze dies durch deine eigenen Daten)
ds <- data.frame(
  Geschlecht = factor(c("männlich", "weiblich", "männlich", "weiblich", "männlich")),
  Haarfarbe = factor(c("blond", "brünett", "blond", "schwarz", "braun")),
  Nationalität = factor(c("Deutsch", "Französisch", "Spanisch", "Italienisch", "Englisch"))
)

# Zeige die ersten paar Zeilen der Daten
head(ds)

# Grafische Darstellung der Daten
library(ggplot2)

# Geschlecht
ggplot(ds, aes(x = Geschlecht)) +
  geom_bar(fill = "skyblue") +
  labs(x = "Geschlecht", y = "Anzahl", title = "Verteilung nach Geschlecht")

# Haarfarbe
ggplot(ds, aes(x = Haarfarbe)) +
  geom_bar(fill = "salmon") +
  labs(x = "Haarfarbe", y = "Anzahl", title = "Verteilung nach Haarfarbe")

# Nationalität
ggplot(ds, aes(x = Nationalität)) +
  geom_bar(fill = "lightgreen") +
  labs(x = "Nationalität", y = "Anzahl", title = "Verteilung nach Nationalität")
