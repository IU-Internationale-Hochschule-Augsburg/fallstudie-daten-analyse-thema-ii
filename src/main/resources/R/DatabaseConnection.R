# Install required packages (uncomment and run once)
# install.packages(c("DBI", "RODBC", "odbc", "dplyr", "dbplyr", "RPostgres"))

library(DBI)
library(RODBC)
library(odbc)
library(dplyr)
library(dbplyr)
library(RPostgres)

# Database connection parameters
dsn_database <- ""  # Specific name of the database
dsn_hostname <- ""  # Hostname
dsn_port <-   # Port number
dsn_uid <- ""  # Username
dsn_pwd <- 

# Connect to the database
database_connection <- function() {
    tryCatch(
        {
            print("Connecting to Database…")
            connec <- dbConnect(RPostgres::Postgres(),  # Use RPostgres directly
                dbname = dsn_database,
                host = dsn_hostname,
                port = dsn_port,
                user = dsn_uid,
                password = dsn_pwd
            )
            print("Database Connected!")
            return(connec)
        },
        error = function(cond) {
            message("Unable to connect to Database: ", cond$message)
            return(NULL)  # Return NULL if connection fails
        }
    )
}


# Load all questions from the database
data_questions <- function(connec) {
    return(dbGetQuery(connec, "SELECT * FROM question"))
}

# Load all answers from the database
data_answers <- function(connec) {
    return(dbGetQuery(connec, "SELECT * FROM answer"))
}

# Disconnect from the database
database_disconnection <- function(connec) {
    dbDisconnect(connec)  # Disconnect from the database
    print("Database Disconnected!")
}

###################################################################################
# Test queries from the database (uncomment to use)
# connec <- database_connection()
# dbListTables(connec)
# questions <- dbGetQuery(connec, "SELECT * FROM question WHERE survey_id = 20001")
# answers <- dbGetQuery(connec, "SELECT * FROM answer")
# answer <- dbGetQuery(connec, "SELECT * FROM answer WHERE question_id = 20001")
# database_disconnection(connec)
###################################################################################