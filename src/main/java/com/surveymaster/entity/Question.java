package com.surveymaster.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;

    private Long surveyId;

    @Column(length = 256)
    private String questionType;

    @Column(length = 1024)
    private String questionText;

    @Column(length = 1024)
    private String description;

    // Saving the answer options for the Radiobutton question type
    @Column(length = 1024)
    private String answerOption1;

    @Column(length = 1024)
    private String answerOption2;

    @Column(length = 1024)
    private String answerOption3;

    @Column(length = 1024)
    private String answerOption4;

    @Column(length = 1024)
    private String answerOption5;

    @Column(length = 1024)
    private String answerOption6;

    @Column(length = 1024)
    private String answerOption7;

    @Column(length = 1024)
    private String answerOption8;

    @Column(length = 1024)
    private String answerOption9;

    @Column(length = 1024)
    private String answerOption10;

    public Question() {
    }

    public Question(Long surveyId, String questionType, String questionText, String description, String answerOption1,
                    String answerOption2, String answerOption3, String answerOption4, String answerOption5,
                    String answerOption6, String answerOption7, String answerOption8, String answerOption9,
                    String answerOption10) {
        this.surveyId = surveyId;
        this.questionType = questionType;
        this.questionText = questionText;
        this.description = description;
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
    }
}
