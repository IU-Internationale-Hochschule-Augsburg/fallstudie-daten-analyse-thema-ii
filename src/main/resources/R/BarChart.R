# Erstelle ein Säulendiagramm mit dem Durchschnitt als Linie
barChartDiagram <- function(diagramTitle, x, y, data) {
    # Increase margin size
    par(mar = c(8, 5, 3, 3))

    barplot(data,
        col = "skyblue",
        main = diagramTitle,
        ylab = y,
        names.arg = x,
        las = 2
    )
    abline(h = mean(data), col = "red", lwd = 2)

    # 1.Zeile ist der Code für die Caption (Kursiver Text unter der Graphik)
    # mtext(text = bquote(italic(.(caption))), side = 1, line = 4, cex = 0.8)
}
