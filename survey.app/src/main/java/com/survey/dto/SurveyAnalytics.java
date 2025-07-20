// SurveyAnalytics.java
package com.survey.dto;

import java.util.List;

public class SurveyAnalytics {
    private Long surveyId;
    private String surveyTitle;
    private int totalResponses;
    private List<QuestionAnalytics> questions;

    // Constructors
    public SurveyAnalytics() {}

    public SurveyAnalytics(Long surveyId, String surveyTitle, int totalResponses, List<QuestionAnalytics> questions) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.totalResponses = totalResponses;
        this.questions = questions;
    }

    // Getters and Setters
    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public int getTotalResponses() {
        return totalResponses;
    }

    public void setTotalResponses(int totalResponses) {
        this.totalResponses = totalResponses;
    }

    public List<QuestionAnalytics> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionAnalytics> questions) {
        this.questions = questions;
    }
}
