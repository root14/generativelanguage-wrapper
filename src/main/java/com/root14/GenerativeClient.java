package com.root14;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.root14.response.GenerativeResponse;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author root14
 * <p>Example usage:</p>
 * <pre>{@code
 * GenerativeClient generativeClient = new GenerativeClient.Builder()
 *         .setApiKey("api-key-here")
 *         .setModel(GenerativeModel.GEMINI_2_PRO_EXPERIMENTAL)
 *         .setTemperature(0.7)
 *         .build();
 *
 * Map<String, String> properties = new HashMap<>();
 * properties.put("solution_for_plant", "string");
 * properties.put("thoughts", "string");
 * properties.put("joke", "string");
 *
 * GenerativeResponse generativeResponse = generativeClient.sendRequest(
 *         "papatyalarim soluyor ne yapmaliyim?",
 *         properties,
 *         List.of("solution_for_plant"));
 * }</pre>
 */
public class GenerativeClient {
    private final String apiKey;
    private final String model;
    private final double temperature;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private GenerativeClient(Builder builder) {
        this.apiKey = builder.apiKey;
        this.model = builder.model;
        this.temperature = builder.temperature;
    }

    /**
     * Builder for GenerativeClient
     * must provide apiKey from ai aistudio.google.com
     * Generative Client uses gemini-2.0 as default
     * (optional) set temperature
     */
    public static class Builder {
        private String apiKey;
        private String model = GenerativeModel.GEMINI_2.getModelName(); // Default model
        private double temperature = 0.7; // Default temperature

        public Builder setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder setModel(GenerativeModel model) {
            this.model = model.getModelName();
            return this;
        }

        public Builder setTemperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public GenerativeClient build() {
            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalArgumentException("API Key is required");
            }
            return new GenerativeClient(this);
        }
    }

    /**
     * Generates a request to the generative language model with dynamic properties and required fields.
     *
     * @param inputText         The input text for the request.
     * @param dynamicProperties A map where keys are property names and values are their types (e.g., "string", "number").
     * @param requiredFields    A list of required property names.
     * @return A {@code GenerativeResponse} object containing the model's response.
     * @throws Exception If there is an issue with the HTTP request.
     */
    public GenerativeResponse sendRequest(String inputText, Map<String, String> dynamicProperties, List<String> requiredFields) throws Exception {
        URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> contents = new HashMap<>();
        contents.put("role", "user");
        contents.put("parts", new Object[]{Map.of("text", inputText)});

        requestBody.put("contents", new Object[]{contents});

        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("temperature", temperature);
        generationConfig.put("maxOutputTokens", 8192);
        generationConfig.put("topK", 64);
        generationConfig.put("topP", 0.95);
        generationConfig.put("responseMimeType", "application/json");

        Map<String, Object> responseSchema = new HashMap<>();
        responseSchema.put("type", "object");

        Map<String, Object> properties = new HashMap<>();
        for (Map.Entry<String, String> entry : dynamicProperties.entrySet()) {
            properties.put(entry.getKey(), Map.of("type", entry.getValue()));
        }

        responseSchema.put("properties", properties);
        responseSchema.put("required", requiredFields.toArray(new String[0]));

        generationConfig.put("responseSchema", responseSchema);
        requestBody.put("generationConfig", generationConfig);

        String jsonInputString = objectMapper.writeValueAsString(requestBody);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        return objectMapper.readValue(connection.getInputStream(), GenerativeResponse.class);
    }

}