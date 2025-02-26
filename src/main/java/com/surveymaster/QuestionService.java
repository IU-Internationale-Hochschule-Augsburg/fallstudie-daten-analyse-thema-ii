package com.surveymaster;

import com.surveymaster.repository.AnswerRepository;
import com.surveymaster.repository.QuestionRepository;
import com.surveymaster.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor

public class QuestionService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public void deleteQuestion(String questionId) {
        final var selectedQuestion = questionRepository.findById(Long.parseLong(questionId)).orElseThrow();
        answerRepository.deleteByQuestionId(selectedQuestion.getQuestionId());
        questionRepository.deleteById(selectedQuestion.getQuestionId());
    }

    public QuestionsView getQuestionsView(String surveyId) {
        // With the surveyID, the corresponding survey is searched for.
        // If it exists, it is saved in the 'survey' variable; otherwise, a NoSuchElementException is thrown
        final var survey = surveyRepository.findById(Long.parseLong(surveyId)).orElseThrow();

        // Setting the ID and title of the survey in questionView
        final QuestionsView questionsView = new QuestionsView();
        questionsView.setSurveyId(survey.getSurveyId());
        questionsView.setTitle(survey.getTitle());

        // The surveyID is also used to search in the questionRepository, as the surveyID is also present in the
        // Question table. Then, the Question is set accordingly
        final var questions = questionRepository.findBySurveyId(Long.parseLong(surveyId));
        questionsView.setQuestions(questions);
        return questionsView;
    }
}
