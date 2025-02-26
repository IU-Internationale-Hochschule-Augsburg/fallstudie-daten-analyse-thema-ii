library(ggplot2)

# Kreisdiagramm erstellen
piechart <- function(data, name, output_path) {
    summe <- sum(data$amount)
    p <- ggplot(data, aes(x = "", y = amount, fill = category)) +
        geom_bar(stat = "identity", width = 1) +
        coord_polar("y", start = 0) +
        geom_text(aes(label = ifelse(amount > 0, paste0(round((amount * 100) / summe), "%\n", amount), "")), position = position_stack(vjust = 0.5)) +
        labs(x = NULL, y = NULL, fill = NULL, title = name)
    ggsave(output_path, p, width = 6, height = 6)
    # cat("Das Diagramm wurde erfolgreich als", output_path, "gespeichert.\n")
    # print(p)
}
