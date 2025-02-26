package com.surveymaster;

import com.surveymaster.entity.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionsView {
    private String title;

    private Long surveyId;

    private List<Question> questions = new ArrayList<>();
}
