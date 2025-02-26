package com.surveymaster.repository;

import com.surveymaster.entity.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {
    void deleteByQuestionId(Long questionId);

    List<Answer> findByQuestionId(Long questionId);

    List<Answer> findAll();
}
