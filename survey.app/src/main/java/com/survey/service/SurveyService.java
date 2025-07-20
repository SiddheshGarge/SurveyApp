// SurveyService.java
package com.survey.service;

import com.survey.dto.*;
import com.survey.entity.*;
import com.survey.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;

    public SurveyService(SurveyRepository surveyRepository,
                         QuestionRepository questionRepository,
                         OptionRepository optionRepository,
                         ResponseRepository responseRepository,
                         AnswerRepository answerRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.responseRepository = responseRepository;
        this.answerRepository = answerRepository;
    }

    public List<Survey> getAllActiveSurveys() {
        return surveyRepository.findByActiveTrue();
    }

    public List<Survey> getSurveysByCreator(User creator) {
        return surveyRepository.findByCreator(creator);
    }

    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    public Survey createSurvey(SurveyCreateRequest request, User creator) {
        Survey survey = new Survey(request.getTitle(), request.getDescription(), creator);
        survey = surveyRepository.save(survey);

        for (int i = 0; i < request.getQuestions().size(); i++) {
            QuestionRequest questionRequest = request.getQuestions().get(i);
            Question question = new Question(questionRequest.getQuestionText(), i + 1, survey);
            question = questionRepository.save(question);

            for (String optionText : questionRequest.getOptions()) {
                Option option = new Option(optionText, question);
                optionRepository.save(option);
            }
        }

        return getSurveyById(survey.getId()); // Reload with all relations
    }

    public void submitResponse(Long surveyId, ResponseSubmitRequest request, User user) {
        Survey survey = getSurveyById(surveyId);

        // Check if user already responded
        if (responseRepository.findBySurveyAndUser(survey, user).isPresent()) {
            throw new RuntimeException("You have already responded to this survey");
        }

        Response response = new Response(survey, user);
        response = responseRepository.save(response);

        for (AnswerRequest answerRequest : request.getAnswers()) {
            Question question = questionRepository.findById(answerRequest.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            Option selectedOption = optionRepository.findById(answerRequest.getSelectedOptionId())
                    .orElseThrow(() -> new RuntimeException("Option not found"));

            Answer answer = new Answer(response, question, selectedOption);
            answerRepository.save(answer);
        }
    }

    public SurveyAnalytics getSurveyAnalytics(Long surveyId, User creator) {
        Survey survey = getSurveyById(surveyId);

        if (!survey.getCreator().getId().equals(creator.getId())) {
            throw new RuntimeException("You are not authorized to view analytics for this survey");
        }

        List<Response> responses = responseRepository.findBySurvey(survey);

        SurveyAnalytics analytics = new SurveyAnalytics();
        analytics.setSurveyId(surveyId);
        analytics.setSurveyTitle(survey.getTitle());
        analytics.setTotalResponses(responses.size());

        Map<Long, QuestionAnalytics> questionAnalytics = new HashMap<>();

        for (Question question : survey.getQuestions()) {
            QuestionAnalytics qAnalytics = new QuestionAnalytics();
            qAnalytics.setQuestionId(question.getId());
            qAnalytics.setQuestionText(question.getQuestionText());

            Map<Long, OptionAnalytics> optionAnalytics = new HashMap<>();

            for (Option option : question.getOptions()) {
                long count = responses.stream()
                        .flatMap(r -> r.getAnswers().stream())
                        .filter(a -> a.getQuestion().getId().equals(question.getId()))
                        .filter(a -> a.getSelectedOption().getId().equals(option.getId()))
                        .count();

                OptionAnalytics oAnalytics = new OptionAnalytics();
                oAnalytics.setOptionId(option.getId());
                oAnalytics.setOptionText(option.getOptionText());
                oAnalytics.setCount(count);

                optionAnalytics.put(option.getId(), oAnalytics);
            }

            qAnalytics.setOptions(new ArrayList<>(optionAnalytics.values()));
            questionAnalytics.put(question.getId(), qAnalytics);
        }

        analytics.setQuestions(new ArrayList<>(questionAnalytics.values()));
        return analytics;
    }
}
