package com.surveymaster;

import com.surveymaster.SavedChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final SavedChartService savedChartService;

    @PostMapping("/save")
    public ResponseEntity<?> saveChartSelections(@RequestBody Map<Long, String> chartSelections,
                                                 @RequestParam Long surveyId,
                                                 @RequestParam Long userId) {
        savedChartService.saveChartSelections(surveyId, userId, chartSelections);
        return ResponseEntity.ok("Diagrammauswahl gespeichert");
    }

    @GetMapping("/load")
    public ResponseEntity<Map<Long, String>> loadChartSelections(@RequestParam Long surveyId,
                                                                 @RequestParam Long userId) {
        return ResponseEntity.ok(savedChartService.getSavedChartSelections(surveyId, userId));
    }
}
