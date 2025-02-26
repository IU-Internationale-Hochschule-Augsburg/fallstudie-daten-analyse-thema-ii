library(dplyr)

# Lade die Datenbankverbindung aus DatabaseConnection.R
source("/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/DatabaseConnection.R", local = TRUE)

# Verbindung zur Datenbank aufbauen
connection <- database_connection()

if (is.null(connection)) {
  stop("❌ Fehler: Verbindung zur Datenbank konnte nicht hergestellt werden! Skript wird gestoppt.")
} else {
  print("✅ Debug: Verbindung zur Datenbank erfolgreich!")
}

# Daten abrufen
data_questions <- function(connection) {
  dbGetQuery(connection, "SELECT * FROM public.questions")
}

data_answers <- function(connection) {
  dbGetQuery(connection, "SELECT answer_id, question_id, user_id, textinput,
                                     answer_option1, answer_option2, answer_option3,
                                     answer_option4, answer_option5, answer_option6,
                                     answer_option7, answer_option8, answer_option9,
                                     answer_option10 FROM public.answer")
}

questions <- data_questions(connection)
answers <- data_answers(connection)

# Prüfen, ob `questions` erfolgreich geladen wurde
if (nrow(questions) == 0) {
  stop("❌ Fehler: `questions` ist leer.")
} else {
  print("✅ Debug: `questions` geladen")
  print(str(questions))
}

# Prüfen, ob `answers` erfolgreich geladen wurde
if (nrow(answers) == 0) {
  stop("❌ Fehler: `answers` ist leer.")
} else {
  print("✅ Debug: `answers` geladen")
  print(str(answers))
}

# Funktion zur Bereinigung der Antwort-Daten
answer_clos_value <- function(answers) {
  required_cols <- paste0("answer_option", 1:10)
  existing_cols <- colnames(answers)
  valid_cols <- intersect(required_cols, existing_cols)

  if (length(valid_cols) == 0) {
    stop("❌ Fehler: Keine gültigen Antwortspalten in `answers` gefunden.")
  }

  answers <- answers[, valid_cols, drop = FALSE]
  return(answers)
}

# Funktion zur Bereinigung der Fragen-Daten
answers_description_clos_value <- function(question, answers) {
  result <- rep(TRUE, ncol(answers))
  fehlende_eintraege <- 10 - length(result)
  zusaetzliche_false <- rep(FALSE, fehlende_eintraege)
  cols_to_keep <- c(result, zusaetzliche_false)
  question <- question[, cols_to_keep, drop = FALSE]
  return(question)
}

# Entfernt unnötige Spalten für Radiobuttons
radio_button_list <- function(answers) {
  answers <- answers %>% relocate(answer_option10, .after = answer_option9)
  cols_to_keep <- c(
    TRUE, TRUE, TRUE, TRUE, TRUE,
    TRUE, TRUE, TRUE, TRUE, TRUE,
    FALSE, FALSE, FALSE, FALSE
  )
  answers <- answers[, cols_to_keep, drop = FALSE]
  return(answers)
}

# Entfernt unnötige Spalten für Checkbox-Antworten
checkbox_list <- function(answers) {
  answers <- answers %>% relocate(answer_option10, .after = answer_option9)
  cols_to_keep <- c(
    TRUE, TRUE, TRUE, TRUE, TRUE,
    TRUE, TRUE, TRUE, TRUE, TRUE,
    FALSE, FALSE, FALSE, FALSE
  )
  answers <- answers[, cols_to_keep, drop = FALSE]
  return(answers)
}

# Entfernt unnötige Spalten für offene Textantworten
open_text_response_list <- function(answers) {
  cols_to_keep <- c(
    FALSE, FALSE, FALSE, FALSE, FALSE,
    FALSE, FALSE, FALSE, FALSE, FALSE,
    FALSE, FALSE, FALSE, TRUE
  )
  answers <- answers[, cols_to_keep, drop = FALSE]
  return(answers)
}

# Bereinigt Checkbox-Antwortoptionen
answer_options_description_checkbox <- function(question) {
  question <- question %>% relocate(answer_option10, .after = answer_option9)
  cols_to_keep <- c(
    FALSE, FALSE, FALSE, TRUE, TRUE,
    TRUE, TRUE, TRUE, TRUE, TRUE,
    TRUE, TRUE, TRUE, FALSE, FALSE
  )
  question <- question[, cols_to_keep, drop = FALSE]
  return(question)
}

# Bereinigt Radiobutton-Antwortoptionen
answer_options_description_radio_button <- function(question) {
  question <- question %>% relocate(answer_option10, .after = answer_option9)
  cols_to_keep <- c(
    FALSE, FALSE, FALSE, TRUE, TRUE,
    TRUE, TRUE, TRUE, TRUE, TRUE,
    TRUE, TRUE, TRUE, FALSE, FALSE
  )
  question <- question[, cols_to_keep, drop = FALSE]
  return(question)
}

# Verbindung zur Datenbank trennen
database_disconnection(connection)
print("✅ Verbindung zur Datenbank geschlossen.")

