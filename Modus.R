#Modus

# Funktion zum Berechnen des Modus
find_mode <- function(x) {
  u <- unique(x)
  tab <- tabulate(match(x, u))
  u[tab == max(tab)]
}


# Berechnung des Modus für einen numerischen Vektor
data_numeric <- c(1, 2, 2, 3, 4, 4, 4, 4, 5, 6)
mode_numeric <- find_mode(data_numeric)
cat("Der Modus des numerischen Vektors ist:", mode_numeric, "\n")

# Histogramm zur Visualisierung der Verteilung
hist(data_numeric, main="Histogramm des numerischen Vektors",
     xlab="Werte", ylab="Häufigkeit", col="skyblue", border="white")

# Markiere den Modus im Histogramm
abline(v = mode_numeric, col = "red", lwd = 2)