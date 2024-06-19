# Pakete laden
library(readxl)
library(ggplot2)
library(reshape2)

# Ordner zum Speichern der Ergebnisse erstellen (Hier werden die erzeugten Diagramme gespeichert)
output_dir <- "~/Desktop/Umfrage_Ergebnis"
if (!dir.exists(output_dir)) {
  dir.create(output_dir)
}

# Alle Charts miteinbinden
#source("PieChart.R") (Aktuell noch Main.R) 
source("Heatmap.R")
source("Liniendiagramm.R")
source("BarChart.R")
source("Versuch.R")
source("Varianz.R")
source("Ordenalskala.R")
source("Nominalskala.R")
source("Modus.R")
source("Median.R")
source("Intervalskala.R")

# Ergebnisse speichern Funktion
save_results <- function(data, column) {
  result_file <- file.path(output_dir, "result.csv")
  write.csv(data, result_file)
  print(paste("Ergebnisse in", result_file, "gespeichert"))
}

# Algorithmus um zu entscheiden welcher Diagramm angezeigt werden soll
choose_plot_type <- function(data, column) {
  print(paste("Analysiere Spalte:", column))
  print(str(data[[column]]))  # Daten der ersten Spalte
  
  if (is.factor(data[[column]]) || is.character(data[[column]])) {
    levels_count <- length(unique(data[[column]]))
    print(paste("Daten erkannt mit", levels_count, "Spalten"))
    
    if (levels_count > 5) {
      print("Verwende Nominalskala-Diagramme")
      create_nominal_scale_charts(data, output_dir)
    } else {
      print("Verwende Kuchendiagramm")
      create_pie_chart(data, output_dir)
    }
  } else if (is.numeric(data[[column]]) && is.atomic(data[[column]])) {
    print("Numerische Daten erkannt")
    if (ncol(data) > 2) {
      print("Verwende Heatmap")
      create_heatmap(data, output_dir)
    } else {
      if (length(unique(data[[column]])) > 10) {
        print("Verwende Histogramm für Modus")
        calculate_mode(data[[column]], output_dir)
      } else {
        print("Verwende Liniendiagramm")
        create_line_chart(data, output_dir)
      }
    }
    if (length(data[[column]]) > 30) {
      print("Berechne Varianz")
      calculate_variance(data[[column]], output_dir)
    } else {
      print("Berechne Median")
      calculate_median(data[[column]], output_dir)
    }
  } else {
    print("Unbekannter oder nicht unterstützter Datentyp")
  }
  
  # Ergebnisse speichern
  save_results(data, column)
}

# Beispiel-Daten importieren (wird entfernt nach H2-Integrierung)
data <- read_excel("/Users/abdullahcicek/Desktop/Mappe2.xlsx", na = "NA")

# Datenstruktur anzeigen
print(str(data))
print(head(data))

# Spaltennamen in der Excel
choose_plot_type(data, "Geschlecht")  # Spalte1
choose_plot_type(data, "Alter")  # Spalte2
choose_plot_type(data, "Jahr")  # Spalte3
