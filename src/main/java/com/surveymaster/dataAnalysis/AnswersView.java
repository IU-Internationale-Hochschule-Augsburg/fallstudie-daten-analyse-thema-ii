package com.surveymaster.dataAnalysis;

import com.surveymaster.entity.Answer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnswersView {

    private String title;
    private Long questionId;
    private List<Answer> answers = new ArrayList<>();

}
