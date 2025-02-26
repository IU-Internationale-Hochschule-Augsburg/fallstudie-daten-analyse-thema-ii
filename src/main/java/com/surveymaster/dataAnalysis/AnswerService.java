package com.surveymaster.dataAnalysis;

import com.surveymaster.repository.AnswerRepository;
import com.surveymaster.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public AnswersView getAnswersView(String questionId) {
        // With the questionId, the corresponding question is searched for.
        // If it exists, it is saved in the 'question' variable; otherwise, a
        // NoSuchElementException is thrown
        final var question = questionRepository.findById(Long.parseLong(questionId)).orElseThrow();

        // Setting the ID and title of the question in answerView
        final AnswersView answersView = new AnswersView();
        answersView.setQuestionId(question.getQuestionId());
        answersView.setTitle(question.getQuestionText());

        // The questionId is also used to search in the answersRepository, as the
        // questionId is also present in the
        // Answer table. Then, the Answer is set accordingly
        final var answers = answerRepository.findAll();
        answersView.setAnswers(answers);
        return answersView;
    }
}
