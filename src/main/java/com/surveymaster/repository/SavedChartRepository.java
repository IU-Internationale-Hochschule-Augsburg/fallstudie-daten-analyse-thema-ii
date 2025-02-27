package com.surveymaster.repository;

import com.surveymaster.entity.SavedChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedChartRepository extends JpaRepository<SavedChart, Long> {
    List<SavedChart> findBySurveyIdAndUserId(Long surveyId, Long userId);
}
