package com.dislang.service.ai;

import java.util.Map;
import java.util.function.Consumer;

public class AIServiceRouter {

    private final Map<String, IAIService> services;

    public AIServiceRouter(IAIService deepseek, IAIService gemini) {
        this.services = Map.of("deepseek", deepseek, "gemini", gemini);
    }

    public void ask(
        String modelName,
        String prompt,
        Consumer<String> callback
    ) {
        IAIService service = services.getOrDefault(
            modelName,
            services.get("deepseek")
        );
        System.out.println("🤖 Đang dùng Model: " + service.getProviderName());
        service.ask(prompt, callback);
    }
}
