// QuestionRequest.java
package com.survey.dto;

import java.util.List;

public class QuestionRequest {
    private String questionText;
    private List<String> options;

    // Constructors
    public QuestionRequest() {}

    public QuestionRequest(String questionText, List<String> options) {
        this.questionText = questionText;
        this.options = options;
    }

    // Getters and Setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
