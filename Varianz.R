# Stichprobenvarianz berechnen
var(data)

# Populationsvarianz berechnen
n <- length(data)
var(data) * (n - 1) / n

# Variationskoeffizient berechnen
cv <- sd(data) / mean(data) * 100