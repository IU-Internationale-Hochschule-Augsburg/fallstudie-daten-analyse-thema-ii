package com.surveymaster;

import lombok.Data;

import java.io.Serializable;

@Data

public class QuestionForm implements Serializable {
    private Long questionId;

    private Long surveyId;

    private String questionType;

    private String questionText = "";

    private String description = "";

    // Saving the answer options
    private String answerOption1 = "";
    private String answerOption2 = "";
    private String answerOption3 = "";
    private String answerOption4 = "";
    private String answerOption5 = "";
    private String answerOption6 = "";
    private String answerOption7 = "";
    private String answerOption8 = "";
    private String answerOption9 = "";
    private String answerOption10 = "";

    public QuestionForm() {
    }
}
