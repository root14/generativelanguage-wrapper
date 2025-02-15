package com.root14.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {
    private List<Part> parts;

    public List<Part> getParts() {
        return parts;
    }


}
