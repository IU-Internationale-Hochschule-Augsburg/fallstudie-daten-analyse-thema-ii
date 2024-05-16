# Beispiel-Daten (ersetze dies durch deine eigenen Daten)
ds <- data.frame(
  Arm = rnorm(5, 50, 20),
  Leg = rnorm(5, 50, 20),
  Chest = rnorm(5, 50, 20),
  Gut = rnorm(5, 50, 20),
  Head = rnorm(5, 50, 20)
)

rn <- c("Arm", "Leg", "Chest", "Gut", "Head")
cn <- c("Ann", "Bob", "Tom", "Joy")

x <- data.matrix(ds, rownames.force = FALSE)

heatmap(x, labRow = rn, labCol = cn, main = "Test Heat Map")


# Installiere das reshape-Paket (falls noch nicht installiert)
# install.packages("reshape")
library(reshape)

# Daten umwandeln
data_melt <- melt(ds)
head(data_melt)  # Zeige die ersten sechs Zeilen der umgewandelten Daten an

# Erstelle die Heatmap mit ggplot2
ggplot(data_melt, aes(x = X2, y = X1, fill = value)) +
  geom_tile() +
  labs(x = "Person", y = "Körperteil", title = "Heatmap mit ggplot2")



# Installiere das plotly-Paket (falls noch nicht installiert)
# install.packages("plotly")
library(plotly)

# Erstelle die Heatmap mit plotly
plot_ly(z = ~x, type = "heatmap") %>%
  layout(xaxis = list(title = "Person"), yaxis = list(title = "Körperteil"),
         title = "Heatmap mit plotly")


