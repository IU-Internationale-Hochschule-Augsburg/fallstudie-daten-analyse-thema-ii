package com.surveymaster;

import lombok.Data;

import java.io.Serializable;

@Data

public class SingleQuestionView implements Serializable {
    Long questionId;

    Long surveyId;

    String questionText = "";

    String questionType;

    String description = "";

    String answerOption1 = "";
    String answerOption2 = "";
    String answerOption3 = "";
    String answerOption4 = "";
    String answerOption5 = "";
    String answerOption6 = "";
    String answerOption7 = "";
    String answerOption8 = "";
    String answerOption9 = "";
    String answerOption10 = "";
}
