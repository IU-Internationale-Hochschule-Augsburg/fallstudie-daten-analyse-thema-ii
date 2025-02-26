library(readxl)
library(ggplot2)

tryCatch({
  
#Excel-Datei in R importieren
data <- read_excel("/Users/abdullahcicek/Desktop/DateiExistiertNicht.xlsx",na="NA",)

age <- subset(data, select = Alter)

percent <- data.frame(table(data$Geschlecht))

ggplot(percent, aes(x="", y=Freq, fill=Var1))+
  geom_bar(stat="identity", color="black")+
  coord_polar("y")+theme_void()+
  geom_label(aes(label=Freq), position=position_stack(vjust=0.5),label.size=0,size=6,show.legend=FALSE)+
  labs(fill="Geschlecht:")+
  theme(legend.text = element_text(size = 15),legend.title = element_text(size = 15))

}, 
error = function(e) {
  if (grepl("does not exist", e$message)) {
    # Fehlerbehandlungsroutine für nicht gefundenen Pfad
    print("Der angegebene Pfad existiert nicht.")
  } else {
    # Allgemeine Fehlerbehandlungsroutine für andere Fehler
    print("Ein anderer Fehler ist aufgetreten:")
    print(e)
  }
})






