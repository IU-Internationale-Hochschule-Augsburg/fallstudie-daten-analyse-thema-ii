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

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyRepository surveyRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final SurveyService surveyService;

    // GetMapping method for the survey view, generating the screen on
    // localhost:8080
    @GetMapping("/survey-admin")
    public String getSurveyAdmin(Model model) {
        final SurveyView surveys = new SurveyView();
        User currentUser = surveyService.getCurrentUser();

        // Retrieve all surveys from the surveyRepository (depending on the User ID) and
        // add each found survey to the collection.
        // This line is responsible for dynamically listing the surveys
        surveyRepository.findAllByUserId(currentUser.getUserId()).forEach(survey -> surveys.getSurveys().add(survey));

        model.addAttribute("surveyView", surveys);
        return "surveyView";
    }

    // GetMapping method for the survey-add view, generating the screen on
    // localhost:8080
    @GetMapping("/add-survey")
    public String loadAddSurveyView(Model model) {
        final SurveyForm surveyForm = new SurveyForm();
        model.addAttribute("survey", surveyForm);
        return "addSurvey";
    }

    // GetMapping for handling the buttons in the survey view (survey-admin)
    @GetMapping("button-survey-handler")
    public String buttonHandler(@RequestParam("buttonHandler") String buttonHandler, Model model) {
        String surveyId;

        // if statement for button handling
        if (buttonHandler.startsWith(",delete_")) {
            // the ID in HTML is appended with an underscore to the button name. Using
            // substring, the ID is split from the label
            surveyId = buttonHandler.substring(8);
            // invocation of the service method to delete the survey with the corresponding
            // ID from the database
            surveyService.deleteSurvey(surveyId);
        } else if (buttonHandler.startsWith(",edit_")) {
            // the ID in HTML is appended with an underscore to the button name. Using
            // substring, the ID is split from the label
            surveyId = buttonHandler.substring(6);
            final QuestionsView questionsView = questionService.getQuestionsView(surveyId);
            model.addAttribute("questionsView", questionsView);
            return "questionsView";
        } else if (buttonHandler.startsWith(",settings_")) {
            // the ID in HTML is appended with an underscore to the button name. Using
            // substring, the ID is split from the label
            surveyId = buttonHandler.substring(10);
            // With the surveyID, the corresponding survey is searched for.
            // If it exists, it is saved in the 'survey' variable; otherwise, a
            // NoSuchElementException is thrown
            final var survey = surveyRepository.findById(Long.parseLong(surveyId)).orElseThrow();
            model.addAttribute("survey", survey);
            // calling the HTML script for the survey settings
            return "surveySettings";
        } else if (buttonHandler.startsWith(",monitoring_")) {
            // the ID in HTML is appended with an underscore to the button name. Using
            // substring, the ID is split from the label
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

    // Upon clicking the Save button in the survey-add view, we redirect to
    // /survey-save,
    // where the data is saved accordingly in the database. Afterward, we return to
    // the surveys view.
    @Transactional
    @PostMapping("/survey-save")
    public String saveSurvey(@ModelAttribute SurveyForm surveyForm, Model model) {
        Survey survey;
        // check if the survey already exists in the database
        if (surveyForm.getSurveyId() != null) {
            // if yes, then we retrieve the current survey with the ID
            survey = surveyRepository.findById(surveyForm.getSurveyId()).orElseThrow();
            // Resetting all attributes again
            survey.setTitle(surveyForm.getTitle());
            survey.setStartDate(surveyForm.getStartDate());
            survey.setEndDate(surveyForm.getEndDate());
            survey.setDescription(surveyForm.getDescription());
            survey.setUserId(surveyService.getCurrentUser().getUserId());
        } else {
            // if the survey does not exist yet, it is newly created and saved
            // fetching the survey data from the SurveyForm
            survey = new Survey(surveyService.getCurrentUser().getUserId(), surveyForm.getTitle(),
                    surveyForm.getStartDate(), surveyForm.getEndDate(), surveyForm.getDescription());
        }
        // writing the data into the database
        surveyRepository.save(survey);
        model.addAttribute("survey", surveyForm);
        // return to the survey-view
        return "redirect:/survey-admin";
    }

    // Upon clicking the Cancel button, we redirect back to the survey view (without
    // saving data)
    @PostMapping("/survey-admin")
    public String loadAddSurveyViewAgain(Model model) {
        model.addAttribute("addSurvey", new SurveyView());
        return "redirect:/survey-admin";
    }
}