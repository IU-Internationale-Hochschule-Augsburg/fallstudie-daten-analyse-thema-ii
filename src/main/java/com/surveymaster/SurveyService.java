package com.surveymaster;

import com.surveymaster.entity.Question;
import com.surveymaster.entity.User;
import com.surveymaster.repository.AnswerRepository;
import com.surveymaster.repository.QuestionRepository;
import com.surveymaster.repository.SurveyRepository;
import com.surveymaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteSurvey(String surveyId) {
        // delete questions in loop
        // when a survey is deleted, its associated questions and answers should also be deleted
        final var selectedQuestions = questionRepository.findBySurveyId(Long.parseLong(surveyId));
        for (final Question selectedQuestion : selectedQuestions) {
            answerRepository.deleteByQuestionId(selectedQuestion.getQuestionId());
            questionRepository.deleteById(selectedQuestion.getQuestionId());
        }


        surveyRepository.deleteById(Long.parseLong(surveyId));
    }

    public User getCurrentUser() {
        // Retrieve the current user from authentication and read their name.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.getUserByUsername(username);
    }
}
