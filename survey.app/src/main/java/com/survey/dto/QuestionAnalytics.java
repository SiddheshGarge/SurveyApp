// QuestionAnalytics.java
package com.survey.dto;

import java.util.List;

public class QuestionAnalytics {
    private Long questionId;
    private String questionText;
    private List<OptionAnalytics> options;

    // Constructors
    public QuestionAnalytics() {}

    public QuestionAnalytics(Long questionId, String questionText, List<OptionAnalytics> options) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options = options;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<OptionAnalytics> getOptions() {
        return options;
    }

    public void setOptions(List<OptionAnalytics> options) {
        this.options = options;
    }
}
