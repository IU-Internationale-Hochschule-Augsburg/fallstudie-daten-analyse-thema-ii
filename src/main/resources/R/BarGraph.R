# Erstelle ein Säulendiagramm mit dem Durchschnitt als Linie
barGraphDiagram <- function(diagramTitle, x, y, data) {
    # Increase margin size
    par(mar = c(5, 8, 3, 3))

    barplot(data,
        col = "skyblue",
        horiz = TRUE,
        main = diagramTitle,
        xlab = y,
        names.arg = x,
        las = 2
    )
    abline(v = mean(data), col = "red", lwd = 2)

    # 1.Zeile ist der Code für die Caption (Kursiver Text unter der Graphik)
    # mtext(text = bquote(italic(.(caption))), side = 1, line = 4, cex = 0.8)
}
