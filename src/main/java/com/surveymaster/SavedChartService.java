package com.surveymaster;

import com.surveymaster.entity.SavedChart;
import com.surveymaster.repository.SavedChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedChartService {

    private final SavedChartRepository savedChartRepository;

    public void saveChartSelections(Long surveyId, Long userId, Map<Long, String> chartSelections) {
        List<SavedChart> savedCharts = chartSelections.entrySet().stream().map(entry -> {
            SavedChart savedChart = new SavedChart();
            savedChart.setSurveyId(surveyId);  // Korrigiert
            savedChart.setQuestionId(entry.getKey());  // Korrigiert
            savedChart.setChartType(entry.getValue());
            savedChart.setUserId(userId);  // Korrigiert
            return savedChart;
        }).collect(Collectors.toList());

        savedChartRepository.saveAll(savedCharts);
    }

    public Map<Long, String> getSavedChartSelections(Long surveyId, Long userId) {
        return savedChartRepository.findBySurveyIdAndUserId(surveyId, userId)
                .stream()
                .collect(Collectors.toMap(
                        SavedChart::getQuestionId,
                        SavedChart::getChartType,
                        (existing, replacement) -> replacement  // Falls doppelter Key, nimm den neuesten Wert
                ));
    }
}
