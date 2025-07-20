// OptionAnalytics.java
package com.survey.dto;

public class OptionAnalytics {
    private Long optionId;
    private String optionText;
    private long count;

    // Constructors
    public OptionAnalytics() {}

    public OptionAnalytics(Long optionId, String optionText, long count) {
        this.optionId = optionId;
        this.optionText = optionText;
        this.count = count;
    }

    // Getters and Setters
    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
