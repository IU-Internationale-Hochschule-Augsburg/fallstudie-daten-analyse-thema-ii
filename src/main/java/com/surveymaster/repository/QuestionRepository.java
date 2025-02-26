package com.surveymaster.repository;

import com.surveymaster.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findBySurveyId(Long surveyId);

    Question findByQuestionId(Long questionId);
}
