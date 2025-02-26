# Lade die R-Skripte mit den benötigten Funktionen
source("/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/DatabaseConnection.R")
source("/Users/abdullahcicek/Desktop/survey-application/src/main/resources/R/AnswerList.R")

algo_compare_answers <- function(question_id_1, question_id_2) {
  library(dplyr)
  library(plotly)
  
  connection <- database_connection()
  questions <- data_questions(connection)
  answers <- data_answers(connection)
  
  print("✅ Debug: answers geladen")
  print(str(answers))
  
  question_1 <- subset(questions, question_id == question_id_1)
  question_2 <- subset(questions, question_id == question_id_2)
  
  answers_questionid_1 <- subset(answers, question_id == question_id_1)
  answers_questionid_2 <- subset(answers, question_id == question_id_2)
  
  if (nrow(answers_questionid_1) > 0) {
    answers_questionid_1 <- answer_clos_value(answers_questionid_1)
  }
  if (nrow(answers_questionid_2) > 0) {
    answers_questionid_2 <- answer_clos_value(answers_questionid_2)
  }
  
  if (nrow(answers_questionid_1) > 0 && nrow(answers_questionid_2) > 0) {
    zero_matrix <- matrix(0, nrow = ncol(answers_questionid_1), ncol = ncol(answers_questionid_2))
    
    for (r1 in seq_len(nrow(answers_questionid_1))) {
      for (c1 in seq_len(ncol(answers_questionid_1))) {
        if (answers_questionid_1[r1, c1] == "Y") {
          for (r2 in seq_len(nrow(answers_questionid_2))) {
            for (c2 in seq_len(ncol(answers_questionid_2))) {
              if (answers_questionid_2[r2, c2] == "Y") {
                zero_matrix[c1, c2] <- zero_matrix[c1, c2] + 1
              }
            }
          }
        }
      }
    }
    
    if (ncol(zero_matrix) > 1 && nrow(zero_matrix) > 1) {
      output_path <- file.path("~/Desktop/survey-application/src/main/resources/static/images/", paste(question_id_1, "_", question_id_2, ".png", sep = ""))
      png(output_path, width = 1000, height = 700)
      
      heatmap(
        zero_matrix,
        Colv = NA,
        Rowv = NA,
        scale = "none",
        col = colorRampPalette(c("#d2cfa3", "#02a702"))(15),
        breaks = seq(1, 15, length.out = 16),
        margins = c(13, 7)
      )
      
      dev.off()
    }
  }
  
  print("🔍 Debug: answers_questionid_1")
  print(answers_questionid_1)
  
  print("🔍 Debug: answers_questionid_2")
  print(answers_questionid_2)
  
  
  database_disconnection(connection)
}

# Testaufruf der Funktion
algo_compare_answers(2, 52)
