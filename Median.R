#Median


#Basis Median
marks <- c(97, 78, 57, 64, 87)
median(marks)  # Berechne den Median

#Median eines Vektors mit NA-Werten: 
#Wenn der Vektor NA-Werte enthält, werden diesw mit na.rm = TRUE ignorieren
marks_with_na <- c(97, 78, 57, 64, 87, NA)
median(marks_with_na, na.rm = TRUE)  # Median ohne NA-Werte

#Median einer Spalte in einem Datenrahmen
data(iris)  # Lade den Iris-Datensatz
median(iris$Sepal.Length)  # Median der Spalte Sepal.Length

#Median nach Gruppen
aggregate(iris$Sepal.Length, list(iris$Species), median)

# Beispiel-Daten für den Median
x3 <- c(1, 2, 3, 4, 5)

#Visualisierung des Medians
hist(x3)  # Histogramm
abline(v = median(x3), col = "red", lwd = 3)  # Medianlinie hinzufügen
text(x = 5, y = 200, "Median von x3", col = "red")  # Text hinzufügen

# Text für die Caption
caption_median <- "Die rote Linie kennzeichnet den Medianwert."

# Code für die Caption (Kursiver Text unter der Graphik)
mtext(text = bquote(italic(.(caption_median))), side = 1, line = 4, cex = 0.8)
