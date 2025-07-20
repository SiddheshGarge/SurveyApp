// ResponseSubmitRequest.java
package com.survey.dto;

import java.util.List;

public class ResponseSubmitRequest {
    private List<AnswerRequest> answers;

    // Constructors
    public ResponseSubmitRequest() {}

    public ResponseSubmitRequest(List<AnswerRequest> answers) {
        this.answers = answers;
    }

    // Getters and Setters
    public List<AnswerRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerRequest> answers) {
        this.answers = answers;
    }
}
