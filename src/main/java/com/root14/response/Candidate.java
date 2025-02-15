package com.root14.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Candidate {
    private Content content;
    private String finishReason;

    public String getText() {
        if (content != null && content.getParts() != null && !content.getParts().isEmpty()) {
            return content.getParts().get(0).getText();
        }
        return null;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
}
