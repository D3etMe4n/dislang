package com.dislang.service.ai;

import java.util.Map;
import java.util.function.Consumer;

public class AIServiceRouter {

    private final Map<String, IAIService> services;

    public AIServiceRouter(IAIService deepseek) {
        this.services = Map.of("deepseek", deepseek);
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
        service.ask(prompt, callback);
    }
}
