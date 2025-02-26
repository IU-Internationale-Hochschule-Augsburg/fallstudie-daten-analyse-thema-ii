package com.surveymaster.dataAnalysis;

import com.surveymaster.QuestionService;
import com.surveymaster.QuestionsView;
import com.surveymaster.entity.Question;
import com.surveymaster.entity.Survey;
import com.surveymaster.repository.AnswerRepository;
import com.surveymaster.repository.QuestionRepository;
import com.surveymaster.repository.SurveyRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Data
public class AnalysisController {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;

    private List<Survey> surveys = new ArrayList<>();

    @GetMapping("/analysis")
    public String loadAnalysis(@RequestParam("surveyId") String surveyId, Model model) {
        final QuestionsView questionsView = questionService.getQuestionsView(surveyId);
        model.addAttribute("questionsView", questionsView);
        final List<Question> questions = questionsView.getQuestions();
        for (Question question : questions) {
            final AnswersView answersView = answerService.getAnswersView(Long.toString(question.getQuestionId()));
            model.addAttribute("answersView" + question, answersView);
        }
        return "analysisView";
    }

    @GetMapping("/compare-questions")
    public String loadCompareQuestions(@RequestParam("surveyId") String surveyId, Model model) {
        // final var questions =
        // questionRepository.findBySurveyId(Long.parseLong(surveyId));
        final var survey = surveyRepository.findById(Long.parseLong(surveyId)).orElseThrow();
        model.addAttribute("survey", survey);
        final QuestionsView questionsView = questionService.getQuestionsView(surveyId);
        model.addAttribute("questionsView", questionsView);
        return "compareView";
    }

    @GetMapping("/heatmap")
    public String loadCompareHeatmap(@RequestParam("document") String document, Model model) {
        String[] parts = document.split("_");
        String question1 = parts[0];
        String question2 = parts[1];

        long question1Long = Long.parseLong(question1);
        long question2Long = Long.parseLong(question2);

        try {
            AnalysisAlgorithm.compareAnswersChart(question1Long, question2Long);
        } catch (NumberFormatException e) {
            System.err.println("Fehler beim Umwandeln des Strings in einen long-Wert: " + e.getMessage());
        }

        var questionTitle1 = questionRepository.findByQuestionId(question1Long);
        var questionTitle2 = questionRepository.findByQuestionId(question2Long);
        var surveyId = questionTitle1.getSurveyId();

        model.addAttribute("survey", surveyId);
        model.addAttribute("title1", questionTitle1.getQuestionText());
        model.addAttribute("title2", questionTitle2.getQuestionText());
        model.addAttribute("filename", question1 + question2);
        return "heatmapView";
    }

    @GetMapping("/button-analysis-handler")
    public String buttonHandler(@RequestParam("buttonHandler") String buttonHandler, Model model) {
        String surveyId;
        if (buttonHandler.startsWith(",back_")) {
            // the ID in HTML is appended with an underscore to the button name. Using
            // substring, the ID is split from the label
            surveyId = buttonHandler.substring(6);
            final QuestionsView questionsView = questionService.getQuestionsView(surveyId);
            model.addAttribute("questionsView", questionsView);
            final List<Question> questions = questionsView.getQuestions();
            for (Question question : questions) {
                final AnswersView answersView = answerService.getAnswersView(Long.toString(question.getQuestionId()));
                model.addAttribute("answersView", answersView);
            }
            final var survey = surveyRepository.findById(Long.parseLong(surveyId)).orElseThrow();
            model.addAttribute("survey", survey);
            return "analysisView";
        }
        return "redirect:/analysisView";
    }
}
