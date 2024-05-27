library(RH2)
library(RJDBC)

myH2 <- H2('org.h2.Driver', 'C:/Program Files(x86)/h2/bin/h2-2.2.224.jar')
## location of h2 file: C:\data\data.h2.db 
con <- dbConnect(H2(), "jdbc:h2:~/test", "sa", "1234")

# create table, populate it and display it
dbGetQuery(con, "select * from ANSWER")

#answer_id <- "select * from ANSWER WHERE COLUMN = 'ANSWER_ID'"
#question_id <- "select * from TABLE WHERE COLUMN = 'QUESTION_ID'"
#checkbox <- "select * from TABLE WHERE COLUMN = 'CHECKBOX'"
#radiobutton <- "select * from TABLE WHERE COLUMN = 'RADIOBUTTON'"
#textinput <- "select * from TABLE WHERE COLUMN = 'TEXTINPUT'"
result = dbGetQuery(con, "SELECT * FROM ANSWER" )

dbDisconnect(con)