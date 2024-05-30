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

# install.packages("reshape")
library(reshape)

# Daten umwandeln
data_melt <- melt(ds)
head(data_melt)  # Zeige die ersten sechs Zeilen der umgewandelten Daten an

# install.packages("plotly")
library(plotly)

# Erstelle die Heatmap mit plotly
plot_ly(z = ~x, type = "heatmap") %>%
  layout(xaxis = list(title = "Person"), yaxis = list(title = "Körperteil"),
         title = "Heatmap mit plotly")