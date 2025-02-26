package com.surveymaster;

import com.surveymaster.entity.Answer;
import com.surveymaster.repository.AnswerRepository;
import com.surveymaster.repository.QuestionRepository;
import com.surveymaster.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Controller
@RequiredArgsConstructor
public class ParticipantSurveyController {
    private final QuestionRepository questionRepository;
    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    // Model attributes are pre-initialized with the request parameters
    @GetMapping("/participant-view")
    public String loadParticipantView(@RequestParam("surveyId") String surveyId, @RequestParam("currentQuestion")
    int currentQuestion, @ModelAttribute ParticipantSurveyView participantSurveyView, Model model) {
        final var questions = questionRepository.findBySurveyId(Long.parseLong(surveyId));

        if (currentQuestion == 0) {
            participantSurveyView.nextUserId();
        }

        if (participantSurveyView.getCurrentQuestion() < questions.size()) {
            final var question = questions.get(participantSurveyView.getCurrentQuestion());
            final var survey = surveyRepository.findById(Long.parseLong(surveyId)).orElseThrow();

            if(survey.getEndDate().isBefore(LocalDate.now())) {
                model.addAttribute("participantSurveyView", participantSurveyView);

                return "surveyExpired";
            } else {
                model.addAttribute("participantSurveyView", participantSurveyView);
                model.addAttribute("survey", survey);
                model.addAttribute("question", question);

                return "participantView";
            }
        } else {
            return "surveyCompletionView";
        }
    }

    @Transactional
    @PostMapping("/save-response")
    public String saveResponse(@ModelAttribute("surveyId") String surveyId, @ModelAttribute ParticipantSurveyView participantSurveyView, Model model) {
        // get last question
        final var lastQuestion = questionRepository.findBySurveyId(Long.parseLong(surveyId)).get(participantSurveyView.getCurrentQuestion());

        //increment counter for the next question
        participantSurveyView.setCurrentQuestion(participantSurveyView.getCurrentQuestion() + 1);

        // Check among all previous answers to see if user x has already submitted an answer to the question with questionId y
        // If this is the case, the old answer will be overwritten, and a new one will not be created
        var currentUser = ParticipantSurveyView.getUserId();
        var currentAnswers = answerRepository.findAll();

        Optional<Answer> optionalAnswer = StreamSupport.stream(currentAnswers.spliterator(), false)
                .filter(answer -> answer.getUserId().equals(currentUser)
                        && answer.getQuestionId().equals(lastQuestion.getQuestionId()))
                .findAny();

        if (optionalAnswer.isPresent()) {
            var mappedAnswer = answerMapper.updateAnswer(participantSurveyView, optionalAnswer.get(), lastQuestion);
            answerRepository.save(mappedAnswer);
            model.addAttribute("optionalAnswer", mappedAnswer);
        } else {
            // fill answer attributes
           var answer = answerMapper.toAnswer(participantSurveyView, lastQuestion);

            // save answer
            answerRepository.save(answer);
            model.addAttribute("answer", answer);
        }

        model.addAttribute("participantSurveyView", participantSurveyView);
        return "redirect:/participant-view?surveyId=" + surveyId + "&currentQuestion=" + participantSurveyView.getCurrentQuestion();
    }
}
