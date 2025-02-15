package com.root14;

public enum GenerativeModel {
    GEMINI_2("gemini-2.0"),
    GEMINI_2_FLASH("gemini-2.0-flash-lite-preview-02-05"),
    GEMINI_2_PRO_EXPERIMENTAL("gemini-2.0-pro-exp-02-05");

    private final String modelName;

    GenerativeModel(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }
}