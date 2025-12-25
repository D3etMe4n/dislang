package com.dislang.service.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AIServiceRouter {

    private final Map<String, IAIService> services = new HashMap<>();

    public AIServiceRouter(IAIService... aiServices) {
        for (IAIService service : aiServices) {
            // Tự động lấy tên từ service để làm Key
            // Ví dụ: deepseek.getProviderName() trả về "deepseek"
            this.services.put(service.getProviderName(), service);

            System.out.println(
                "🔌 Đã nạp AI Service: " + service.getProviderName()
            );
        }
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
