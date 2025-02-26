package com.surveymaster;

import com.surveymaster.entity.Question;
import com.surveymaster.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    // GetMapping method for the Add-Question screen, loading the Add-Question view
    @PostMapping("/add-question")
    public String loadAddQuestion(@RequestParam("surveyId") String surveyId, Model model) {
        final SingleQuestionView singleQuestion = new SingleQuestionView();
        singleQuestion.setSurveyId(Long.parseLong(surveyId));
        model.addAttribute("addQuestion", singleQuestion);
        return "addQuestion";
    }

    // GetMapping method for the question-view, to load the view after saving a new question
    @GetMapping("/questions-view")
    public String loadQuestionsView(@RequestParam("surveyId") String surveyId, Model model) {
        final QuestionsView questionsView = questionService.getQuestionsView(surveyId);
        model.addAttribute("questionsView", questionsView);
        return "questionsView";
    }

    @GetMapping("/button-question-handler")
    public String buttonQuestionHandler(@RequestParam("buttonQuestionHandler") String buttonQuestionHandler, Model model) {
        String questionId;
        Question selectedQuestion;
        QuestionsView questionsView;
        // if statement for button handling
        if (buttonQuestionHandler.startsWith(",delete_")) {
            questionId = buttonQuestionHandler.substring(8);
            selectedQuestion = questionRepository.findById(Long.parseLong(questionId)).orElseThrow();
            questionService.deleteQuestion(questionId);
            questionsView = questionService.getQuestionsView(String.valueOf(selectedQuestion.getSurveyId()));
            model.addAttribute("questionsView", questionsView);
        } else if (buttonQuestionHandler.startsWith(",edit_")) {
            questionId = buttonQuestionHandler.substring(6);
            addQuestionViewToModel(model, questionId);
            final var question = questionRepository.findById(Long.parseLong((questionId))).orElseThrow();
            model.addAttribute("question", question);
            return "questionSettings";
        }
        return "questionsView";
    }

    // POST mapping function for saving question data
    @Transactional
    @PostMapping("/question-save")
    public String saveQuestion(@ModelAttribute SingleQuestionView questionForm, Model model) {
        Question question;

        if (questionForm.getQuestionId() == null) {
            question = new Question(questionForm.getSurveyId(), questionForm.getQuestionType(), questionForm.getQuestionText(),
                    questionForm.getDescription(), questionForm.getAnswerOption1(), questionForm.getAnswerOption2(),
                    questionForm.getAnswerOption3(), questionForm.getAnswerOption4(), questionForm.getAnswerOption5(),
                    questionForm.getAnswerOption6(), questionForm.getAnswerOption7(), questionForm.getAnswerOption8(),
                    questionForm.getAnswerOption9(), questionForm.getAnswerOption10());
        } else {
            question = questionRepository.findById(questionForm.getQuestionId()).orElseThrow();

            question.setSurveyId(questionForm.getSurveyId());
            question.setQuestionText(questionForm.getQuestionText());
            question.setQuestionType(questionForm.getQuestionType());
            question.setDescription(questionForm.getDescription());
            question.setAnswerOption1(questionForm.getAnswerOption1());
            question.setAnswerOption2(questionForm.getAnswerOption2());
            question.setAnswerOption3(questionForm.getAnswerOption3());
            question.setAnswerOption4(questionForm.getAnswerOption4());
            question.setAnswerOption5(questionForm.getAnswerOption5());
            question.setAnswerOption6(questionForm.getAnswerOption6());
            question.setAnswerOption7(questionForm.getAnswerOption7());
            question.setAnswerOption8(questionForm.getAnswerOption8());
            question.setAnswerOption9(questionForm.getAnswerOption9());
            question.setAnswerOption10(questionForm.getAnswerOption10());
        }
        questionRepository.save(question);
        model.addAttribute("question", questionForm);
        return "redirect:/questions-view?surveyId=" + questionForm.surveyId;
    }

    // Upon clicking the Cancel button, we redirect back to the questions view (without saving data)
    @PostMapping("/questions-view")
    public String loadAddSurveyViewAgain(Model model) {
        model.addAttribute("questionsView", new QuestionsView());
        return "redirect:/questions-view";
    }

    private void addQuestionViewToModel(Model model, String questionId) {
        Question selectedQuestion;
        QuestionsView questionsView;
        selectedQuestion = questionRepository.findById(Long.parseLong(questionId)).orElseThrow();
        questionsView = questionService.getQuestionsView(String.valueOf(selectedQuestion.getSurveyId()));
        model.addAttribute("questionsView", questionsView);
    }
}
