package com.dislang.service.ai.impl;

import com.dislang.service.ai.IAIService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

public class GeminiService implements IAIService {

    private final String apiKey;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String BASE_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    public GeminiService(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getProviderName() {
        return "gemini";
    }

    @Override
    public void ask(String prompt, Consumer<String> callback) {
        try {
            // 1. Tạo node gốc {}
            ObjectNode rootNode = mapper.createObjectNode();

            // 2. Tạo mảng "contents": []
            ArrayNode contentsArray = rootNode.putArray("contents");

            // 3. Tạo object con trong contents: {}
            ObjectNode contentObject = contentsArray.addObject();

            // 4. Tạo mảng "parts" bên trong object con: "parts": []
            ArrayNode partsArray = contentObject.putArray("parts");

            // 5. Tạo object chứa text: { "text": "..." }
            ObjectNode textObject = partsArray.addObject();
            textObject.put("text", prompt);

            String requestBody = mapper.writeValueAsString(rootNode);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + apiKey)) // API Key nằm trên URL
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            // 3. Gửi và xử lý kết quả
            httpClient
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    try {
                        JsonNode responseNode = mapper.readTree(responseBody);

                        if (responseNode.has("error")) {
                            String msg = responseNode
                                .get("error")
                                .get("message")
                                .asText();
                            callback.accept("❌ Gemini Error: " + msg);
                            return;
                        }

                        // Parse kết quả: candidates[0].content.parts[0].text
                        if (responseNode.has("candidates")) {
                            String text = responseNode
                                .get("candidates")
                                .get(0)
                                .get("content")
                                .get("parts")
                                .get(0)
                                .get("text")
                                .asText();
                            callback.accept(text);
                        } else {
                            callback.accept(
                                "⚠️ Gemini không trả lời. Raw: " + responseBody
                            );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.accept(
                            "❌ Lỗi Parse JSON Gemini: " + e.getMessage()
                        );
                    }
                });
        } catch (Exception e) {
            callback.accept("Error when connecting to Gemini");
        }
    }
}
