// SurveyCreateRequest.java
package com.survey.dto;

import java.util.List;

public class SurveyCreateRequest {
    private String title;
    private String description;
    private List<QuestionRequest> questions;

    // Constructors
    public SurveyCreateRequest() {}

    public SurveyCreateRequest(String title, String description, List<QuestionRequest> questions) {
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionRequest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionRequest> questions) {
        this.questions = questions;
    }
}
