package com.dislang.service.ai.impl;

import com.dislang.service.ai.IAIService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

public class DeepSeekService implements IAIService {

    private final String apiKey;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String URL =
        "https://api.deepseek.com/v1/chat/completions"; // Check URL chuẩn của DeepSeek

    public DeepSeekService(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getProviderName() {
        return "DeepSeek";
    }

    @Override
    public void ask(String prompt, Consumer<String> callback) {
        try {
            ObjectNode root = mapper.createObjectNode();
            root.put("model", "deepseek-chat");
            ArrayNode messages = root.putArray("messages");
            messages.addObject().put("role", "user").put("content", prompt);

            String jsonBody = mapper.writeValueAsString(root);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            this.httpClient.sendAsync(
                    request,
                    HttpResponse.BodyHandlers.ofString()
                )
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    // TODO: Parse this json before call
                    callback.accept("DeepSeek: " + response);
                });
        } catch (Exception e) {
            callback.accept("Lỗi gọi API: " + e.getMessage());
        }
    }
}
