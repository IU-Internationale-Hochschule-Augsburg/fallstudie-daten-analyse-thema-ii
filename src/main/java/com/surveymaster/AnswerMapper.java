package com.surveymaster;

import com.surveymaster.entity.Answer;
import com.surveymaster.entity.Question;
import lombok.NonNull;
import org.springframework.stereotype.Component;

// AnswerMapper is a mapper that maps the inputs from the user interface to the database entity.
@Component
public class AnswerMapper {
    public Answer updateAnswer(@NonNull ParticipantSurveyView participantSurveyView, @NonNull Answer optionalAnswer, @NonNull Question lastQuestion) {
        if (lastQuestion.getQuestionType().equals("checkbox")) {
            optionalAnswer.setAnswerOption1(participantSurveyView.getCheckBoxAnswerOption1());
            optionalAnswer.setAnswerOption2(participantSurveyView.getCheckBoxAnswerOption2());
            optionalAnswer.setAnswerOption3(participantSurveyView.getCheckBoxAnswerOption3());
            optionalAnswer.setAnswerOption4(participantSurveyView.getCheckBoxAnswerOption4());
            optionalAnswer.setAnswerOption5(participantSurveyView.getCheckBoxAnswerOption5());
            optionalAnswer.setAnswerOption6(participantSurveyView.getCheckBoxAnswerOption6());
            optionalAnswer.setAnswerOption7(participantSurveyView.getCheckBoxAnswerOption7());
            optionalAnswer.setAnswerOption8(participantSurveyView.getCheckBoxAnswerOption8());
            optionalAnswer.setAnswerOption9(participantSurveyView.getCheckBoxAnswerOption9());
            optionalAnswer.setAnswerOption10(participantSurveyView.getCheckBoxAnswerOption10());
        } else if (lastQuestion.getQuestionType().equals("radiobutton")) {
            optionalAnswer.setAnswerOption1(participantSurveyView.getRadioButtonAnswerOption1());
            optionalAnswer.setAnswerOption2(participantSurveyView.getRadioButtonAnswerOption2());
            optionalAnswer.setAnswerOption3(participantSurveyView.getRadioButtonAnswerOption3());
            optionalAnswer.setAnswerOption4(participantSurveyView.getRadioButtonAnswerOption4());
            optionalAnswer.setAnswerOption5(participantSurveyView.getRadioButtonAnswerOption5());
            optionalAnswer.setAnswerOption6(participantSurveyView.getRadioButtonAnswerOption6());
            optionalAnswer.setAnswerOption7(participantSurveyView.getRadioButtonAnswerOption7());
            optionalAnswer.setAnswerOption8(participantSurveyView.getRadioButtonAnswerOption8());
            optionalAnswer.setAnswerOption9(participantSurveyView.getRadioButtonAnswerOption9());
            optionalAnswer.setAnswerOption10(participantSurveyView.getRadioButtonAnswerOption10());
        } else {
            optionalAnswer.setTextinput(participantSurveyView.getTextinput());
        }

        return optionalAnswer;
    }

    public Answer toAnswer(@NonNull ParticipantSurveyView participantSurveyView, @NonNull Question lastQuestion) {
        Answer answer;
        if (lastQuestion.getQuestionType().equals("checkbox")) {
            answer = new Answer(lastQuestion.getQuestionId(), participantSurveyView.getCheckBoxAnswerOption1(),
                    participantSurveyView.getCheckBoxAnswerOption2(), participantSurveyView.getCheckBoxAnswerOption3(),
                    participantSurveyView.getCheckBoxAnswerOption4(), participantSurveyView.getCheckBoxAnswerOption5(),
                    participantSurveyView.getCheckBoxAnswerOption6(), participantSurveyView.getCheckBoxAnswerOption7(),
                    participantSurveyView.getCheckBoxAnswerOption8(), participantSurveyView.getCheckBoxAnswerOption9(),
                    participantSurveyView.getCheckBoxAnswerOption10());
        } else if (lastQuestion.getQuestionType().equals("radiobutton")) {
            answer = new Answer(lastQuestion.getQuestionId(), participantSurveyView.getRadioButtonAnswerOption1(),
                    participantSurveyView.getRadioButtonAnswerOption2(), participantSurveyView.getRadioButtonAnswerOption3(),
                    participantSurveyView.getRadioButtonAnswerOption4(), participantSurveyView.getRadioButtonAnswerOption5(),
                    participantSurveyView.getRadioButtonAnswerOption6(), participantSurveyView.getRadioButtonAnswerOption7(),
                    participantSurveyView.getRadioButtonAnswerOption8(), participantSurveyView.getRadioButtonAnswerOption9(),
                    participantSurveyView.getRadioButtonAnswerOption10());
        } else {
            //Type: open text response
            answer = new Answer(lastQuestion.getQuestionId(), participantSurveyView.getTextinput());
        }
        answer.setUserId(ParticipantSurveyView.getUserId());

        return answer;
    }
}
