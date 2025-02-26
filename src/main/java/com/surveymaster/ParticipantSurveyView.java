package com.surveymaster;

import lombok.Data;

/* This is a data transfer object to store the answer options for the radio button and checkbox question types separately
in variables. The reason for the dto is that otherwise, in the HTML script, the same answer option objects
are accessed twice (for radio button and checkbox), which leads to incorrect storage of options in the H2 database. */
@Data
public class ParticipantSurveyView {
    private static Long userId = 1000L;

    private Long surveyId;

    // counter
    private int currentQuestion;

    private String radioButtonAnswerOption1 = "N";
    private String radioButtonAnswerOption2 = "N";
    private String radioButtonAnswerOption3 = "N";
    private String radioButtonAnswerOption4 = "N";
    private String radioButtonAnswerOption5 = "N";
    private String radioButtonAnswerOption6 = "N";
    private String radioButtonAnswerOption7 = "N";
    private String radioButtonAnswerOption8 = "N";
    private String radioButtonAnswerOption9 = "N";
    private String radioButtonAnswerOption10 = "N";

    private String checkBoxAnswerOption1 = "N";
    private String checkBoxAnswerOption2 = "N";
    private String checkBoxAnswerOption3 = "N";
    private String checkBoxAnswerOption4 = "N";
    private String checkBoxAnswerOption5 = "N";
    private String checkBoxAnswerOption6 = "N";
    private String checkBoxAnswerOption7 = "N";
    private String checkBoxAnswerOption8 = "N";
    private String checkBoxAnswerOption9 = "N";
    private String checkBoxAnswerOption10 = "N";

    private String textinput = "";

    public void nextUserId() {
        userId++;
    }

    public static Long getUserId() {
        return userId;
    }
}
