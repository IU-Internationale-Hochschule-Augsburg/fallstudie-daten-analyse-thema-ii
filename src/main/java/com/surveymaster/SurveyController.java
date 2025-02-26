package com.surveymaster;

import com.surveymaster.dataAnalysis.AnalysisAlgorithm;
import com.surveymaster.dataAnalysis.AnswerService;
import com.surveymaster.dataAnalysis.AnswersView;
import com.surveymaster.entity.Question;
import com.surveymaster.entity.Survey;
import com.surveymaster.entity.User;
import com.surveymaster.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyRepository surveyRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final SurveyService surveyService;

    @GetMapping("/survey-admin")
    public String getSurveyAdmin(Model model) {
        final SurveyView surveys = new SurveyView();
        User currentUser = surveyService.getCurrentUser();

        surveyRepository.findAllByUserId(currentUser.getUserId()).forEach(survey -> surveys.getSurveys().add(survey));

        model.addAttribute("surveyView", surveys);
        return "surveyView";
    }

    @GetMapping("/add-survey")
    public String loadAddSurveyView(Model model) {
        final SurveyForm surveyForm = new SurveyForm();
        model.addAttribute("survey", surveyForm);
        return "addSurvey";
    }

    @GetMapping("button-survey-handler")
    public String buttonHandler(@RequestParam("buttonHandler") String buttonHandler, Model model) {
        String surveyId;

        if (buttonHandler.startsWith(",delete_")) {
            surveyId = buttonHandler.substring(8);
            surveyService.deleteSurvey(surveyId);
        } else if (buttonHandler.startsWith(",edit_")) {
            surveyId = buttonHandler.substring(6);
            final QuestionsView questionsView = questionService.getQuestionsView(surveyId);
            model.addAttribute("questionsView", questionsView);
            return "questionsView";
        } else if (buttonHandler.startsWith(",settings_")) {
            surveyId = buttonHandler.substring(10);
            final var survey = surveyRepository.findById(Long.parseLong(surveyId)).orElseThrow();
            model.addAttribute("survey", survey);
            return "surveySettings";
        } else if (buttonHandler.startsWith(",monitoring_")) {
            surveyId = buttonHandler.substring(12);
            final QuestionsView questionsView = questionService.getQuestionsView(surveyId);
            model.addAttribute("questionsView", questionsView);
            final List<Question> questions = questionsView.getQuestions();
            for (Question question : questions) {
                final AnswersView answersView = answerService.getAnswersView(Long.toString(question.getQuestionId()));
                model.addAttribute("answersView", answersView);
            }
            final var survey = surveyRepository.findById(Long.parseLong(surveyId)).orElseThrow();
            model.addAttribute("survey", survey);
            AnalysisAlgorithm.answersChart(Long.parseLong(surveyId));
            return "analysisView";
        }
        return "redirect:/survey-admin";
    }

    @Transactional
    @PostMapping("/survey-save")
    public String saveSurvey(@ModelAttribute SurveyForm surveyForm, Model model) {
        Survey survey;

        if (surveyForm.getSurveyId() != null) {
            survey = surveyRepository.findById(surveyForm.getSurveyId()).orElseThrow();
            survey.setTitle(surveyForm.getTitle());
            survey.setStartDate(surveyForm.getStartDate());
            survey.setEndDate(surveyForm.getEndDate());
            survey.setDescription(surveyForm.getDescription());
            survey.setUserId(surveyService.getCurrentUser().getUserId());
        } else {
            survey = new Survey(surveyService.getCurrentUser().getUserId(), surveyForm.getTitle(),
                    surveyForm.getStartDate(), surveyForm.getEndDate(), surveyForm.getDescription());
        }

        surveyRepository.save(survey);
        model.addAttribute("survey", surveyForm);
        return "redirect:/survey-admin";
    }

    @PostMapping("/survey-admin")
    public String loadAddSurveyViewAgain(Model model) {
        model.addAttribute("addSurvey", new SurveyView());
        return "redirect:/survey-admin";
    }

    // **SPEICHERN DER DIAGRAMM-AUSWAHL**
    @PostMapping("/save-chart-selection")
    public ResponseEntity<String> saveChartSelection(@RequestBody Map<String, String> requestData, HttpSession session) {
        try {
            String questionId = requestData.get("questionId");
            String chartType = requestData.get("chartType");

            if (chartType == null || questionId == null) {
                return ResponseEntity.badRequest().body("Ungültige Daten: Frage-ID oder Diagrammtyp fehlt");
            }

            // Holt aktuelle Auswahl aus der Session
            Map<String, String> selectedCharts = (Map<String, String>) session.getAttribute("selectedCharts");
            if (selectedCharts == null) {
                selectedCharts = new HashMap<>();
            }

            // Speichert die Auswahl mit vollständigem Dateinamen (z.B. "102_histogram.png")
            String fileName = questionId + "_" + chartType + ".png";
            selectedCharts.put(questionId, fileName);

            session.setAttribute("selectedCharts", selectedCharts);

            // Debugging-Logs
            System.out.println("✅ Diagramm gespeichert: Frage " + questionId + " -> " + fileName);
            System.out.println("📊 Aktuelle gespeicherte Diagramme: " + selectedCharts);

            return ResponseEntity.ok("Gespeichert: Frage " + questionId + " - " + fileName);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Ungültige Frage-ID");
        }
    }

    // **REPORT SEITE**: Holt gespeicherte Diagramme aus der Session
    @GetMapping("/report")
    public String showReport(Model model, HttpSession session) {
        // Holt die gespeicherten Diagramme aus der Session
        Map<String, String> selectedCharts = (Map<String, String>) session.getAttribute("selectedCharts");

        if (selectedCharts == null) {
            selectedCharts = new HashMap<>();
        }

        // Versuch, die aktuelle Survey-ID aus der Session zu holen
        Survey currentSurvey = (Survey) session.getAttribute("currentSurvey");
        if (currentSurvey == null) {
            // Falls nicht vorhanden, versuche es über ein gespeichertes Attribut (falls gesetzt)
            Long surveyId = (Long) session.getAttribute("surveyId");
            if (surveyId != null) {
                currentSurvey = surveyRepository.findById(surveyId).orElse(null);
            }
        }

        // Falls survey immer noch null ist, muss ein Fehler vermieden werden
        if (currentSurvey == null) {
            model.addAttribute("survey", new Survey()); // Leeres Objekt, um Fehler zu vermeiden
        } else {
            model.addAttribute("survey", currentSurvey);
        }

        model.addAttribute("selectedCharts", selectedCharts);
        return "reportView";
    }
}
