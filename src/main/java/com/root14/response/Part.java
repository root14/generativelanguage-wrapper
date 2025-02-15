package com.root14.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Part {
    private String text;

    public String getText() {
        return text;
    }
}
