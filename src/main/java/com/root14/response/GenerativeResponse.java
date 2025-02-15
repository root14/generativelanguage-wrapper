package com.root14.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerativeResponse {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;

    public String getResponseText() {
        if (candidates != null && !candidates.isEmpty()) {
            return candidates.get(0).getText();
        }
        return null;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public UsageMetadata getUsageMetadata() {
        return usageMetadata;
    }

    public void setUsageMetadata(UsageMetadata usageMetadata) {
        this.usageMetadata = usageMetadata;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }
}
