# 📌 Source der benötigten R-Skripte
source("/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/AnswerList.R")
source("/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/DatabaseConnection.R")
source("/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/BarChart.R")
source("/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/BarGraph.R")
source("/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/PieChart.R")

library(dplyr)
library(tm)
library(wordcloud)
library(RColorBrewer)

# 🔥 Hauptfunktion zur Analyse der Antworten
algo_answers <- function(survey_id) {
  connection <- database_connection()
  questions <- data_questions(connection)
  answers <- data_answers(connection)
  database_disconnection(connection)

  survey_questions <- subset(questions, questions$survey_id == survey_id)

  if (nrow(survey_questions) == 0) {
    print("⚠️ Keine Fragen für diese Umfrage gefunden!")
    return()
  }

  for (i in seq_len(nrow(survey_questions))) {
    row <- survey_questions[i, ]
    question_id <- row$question_id
    question_type <- row$question_type
    question_text <- row$question_text

    print(paste("🛠️ Verarbeite Frage:", question_id, "vom Typ", question_type))

    answers_questionid <- answers[answers$question_id == question_id, ]

    if (nrow(answers_questionid) == 0) {
      print(paste("⚠️ Keine Antworten für Frage", question_id, "-> Überspringe"))
      next
    }

    # 📊 Falls Checkbox oder Radiobutton, erstelle Pie und Bar Chart
    if (question_type == "radiobutton" || question_type == "checkbox") {
      print("📊 Generiere Diagramme für Radiobuttons und Checkboxen")

      if (question_type == "radiobutton") {
        answers_data <- radio_button_list(answers_questionid)
        answers_description <- answer_options_description_radio_button(row)
        answers_counts <- colSums(answers_data == "Y")
      } else {
        answers_data <- checkbox_list(answers_questionid)
        answers_description <- answer_options_description_checkbox(row)
        answers_counts <- colSums(answers_data == "Y")
      }

      select_Chart(question_id, question_text, answers_description, answers_counts)
    }
    # 🔠 Falls Open Text Response, erstelle Word Cloud
    else if (question_type == "open text response") {
      print("📝 Erstelle Word Cloud für offene Antworten")
      answers_text <- answers_questionid$textinput
      wordcloud_path <- paste0("./target/classes/static/images/", question_id, "_histogram.png")
      wordCloudDiagram(question_id, question_text, answers_text, wordcloud_path)
    }
    else {
      print(paste("❌ Unbekannter Fragetyp für Frage", question_id))
    }
  }
}

select_Chart <- function(question_id, question_text, answers_description, answers_counts) {
   if (length(answers_description) == 0 || length(answers_counts) == 0) {
     print("❌ Fehler: Keine Antworten oder keine Labels vorhanden, kein Diagramm wird erstellt.")
     return()
   }

   # 🔹 Labels extrahieren
   x_labels <- as.character(unlist(answers_description[, grep("^answer_option", colnames(answers_description)), drop = FALSE]))
   x_labels <- x_labels[x_labels != ""]  # Entfernt leere Strings

   print(paste("📌 Originale Labels:", paste(x_labels, collapse=", ")))
   print(paste("📌 Originale Antwortwerte:", paste(answers_counts, collapse=", ")))

   # **Fix: Stelle sicher, dass answers_counts genau den Labels entspricht**
   answers_counts_named <- setNames(as.numeric(answers_counts[-1]), x_labels) # Ignoriere das erste NA
   answers_counts_final <- rep(0, length(x_labels)) # Erstelle eine 0-Liste mit korrekter Länge
   names(answers_counts_final) <- x_labels

   # Korrekte Werte zuweisen
   for (label in names(answers_counts_named)) {
     if (label %in% names(answers_counts_final)) {
       answers_counts_final[label] <- answers_counts_named[label]
     }
   }

   answers_counts <- answers_counts_final

   print(paste("📌 Bereinigte Labels:", paste(names(answers_counts), collapse=", ")))
   print(paste("📌 Bereinigte Antwortwerte:", paste(answers_counts, collapse=", ")))

   if (length(x_labels) != length(answers_counts)) {
     print("❌ Fehler: X-Labels und Y-Werte haben nicht die gleiche Länge!")
     return()
   }

   # 📂 Speicherorte definieren
   output_path_pie <- paste0("./target/classes/static/images/", question_id, "_pie.png")
   output_path_bar <- paste0("./target/classes/static/images/", question_id, "_bar.png")

   # 🎯 Pie Chart erstellen
   print("🎯 Erstelle Kreisdiagramm (PieChart)")
   data <- data.frame("category" = names(answers_counts), "amount" = as.numeric(answers_counts))
   rownames(data) <- NULL
   piechart(data, question_text, output_path_pie)

   # 📊 Bar Chart erstellen
   print("📊 Erstelle Balkendiagramm (BarChart)")
   png(output_path_bar, width=800, height=600)
   barGraphDiagram(question_text, names(answers_counts), "Anzahl", as.numeric(answers_counts))
   dev.off()
}

# 🔠 Word Cloud Funktion für offene Textantworten
wordCloudDiagram <- function(question_id, question_text, answers_text, output_path) {
  print("📌 Generiere Word Cloud für offene Antworten")

  # Wenn die Antworten nur Zahlen enthalten, behandeln wir sie als numerische Werte
  numbers_only <- all(grepl("^[0-9]+$", answers_text))  # Prüft, ob alle Einträge nur Zahlen sind

  if (numbers_only) {
    print("🔢 Zahlenwerte erkannt! Erstelle stattdessen eine Häufigkeitsverteilung.")

    # Konvertiere die Zahlen in numerische Werte
    num_values <- as.numeric(answers_text)

    # Erstelle ein Histogramm statt einer Word Cloud
    png(output_path, width=800, height=600)
    hist(num_values, breaks=10, col="blue", main=paste("Häufigkeit von", question_text),
         xlab="Alter", ylab="Anzahl", border="white")
    dev.off()

    print(paste("📌 Histogramm gespeichert unter:", output_path))
  } else {
    print("📝 Standard-Word Cloud für Textantworten")

    text_data <- paste(answers_text, collapse=" ")

    corpus <- Corpus(VectorSource(text_data))
    corpus <- tm_map(corpus, content_transformer(tolower))
    corpus <- tm_map(corpus, removePunctuation)
    corpus <- tm_map(corpus, removeWords, stopwords("de"))  # Deutsch

    tdm <- TermDocumentMatrix(corpus)
    m <- as.matrix(tdm)
    word_freqs <- sort(rowSums(m), decreasing=TRUE)

    if (length(word_freqs) == 0) {
      print("❌ Keine relevanten Wörter gefunden! Word Cloud wird nicht erstellt.")
      return()
    }

    png(output_path, width=800, height=800)
    wordcloud(names(word_freqs), freq=word_freqs, min.freq=1, colors=brewer.pal(8, "Dark2"))
    dev.off()

    print(paste("📌 Word Cloud gespeichert unter:", output_path))
  }
}

# 🔍 Verbindung zur Datenbank und Ausführung
connection <- database_connection()
questions <- data_questions(connection)
answers <- data_answers(connection)
database_disconnection(connection)

# 🚀 Starte Analyse
algo_answers(2)
