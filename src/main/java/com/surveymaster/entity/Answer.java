package com.surveymaster.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data

public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long answerId;

    private Long questionId;

    private Long userId;

    // We store the checked state of the answer options in the database using the characters 'Y' for yes and 'N' for no
    private String answerOption1;
    private String answerOption2;
    private String answerOption3;
    private String answerOption4;
    private String answerOption5;
    private String answerOption6;
    private String answerOption7;
    private String answerOption8;
    private String answerOption9;
    private String answerOption10;

    @Column(length = 1024)
    private String textinput;

    public Answer() {
    }

    public Answer(Long questionId, String answerOption1, String answerOption2, String answerOption3, String answerOption4,
                  String answerOption5, String answerOption6, String answerOption7, String answerOption8, String answerOption9, String answerOption10) {
        this.questionId = questionId;
        this.answerOption1 = answerOption1;
        this.answerOption2 = answerOption2;
        this.answerOption3 = answerOption3;
        this.answerOption4 = answerOption4;
        this.answerOption5 = answerOption5;
        this.answerOption6 = answerOption6;
        this.answerOption7 = answerOption7;
        this.answerOption8 = answerOption8;
        this.answerOption9 = answerOption9;
        this.answerOption10 = answerOption10;
        this.textinput = "";
    }

    public Answer(Long questionId, String textinput) {
        this.questionId = questionId;
        this.textinput = textinput;
        this.answerOption1 = "";
        this.answerOption2 = "";
        this.answerOption3 = "";
        this.answerOption4 = "";
        this.answerOption5 = "";
        this.answerOption6 = "";
        this.answerOption7 = "";
        this.answerOption8 = "";
        this.answerOption9 = "";
        this.answerOption10 = "";
    }
}
