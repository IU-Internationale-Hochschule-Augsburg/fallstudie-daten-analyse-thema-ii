package com.surveymaster.repository;

import com.surveymaster.entity.Survey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends CrudRepository<Survey, Long> {
    List<Survey> findAllByUserId(Long userId);
}
