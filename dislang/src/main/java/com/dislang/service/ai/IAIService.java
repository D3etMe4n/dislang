package com.dislang.service.ai;

import java.util.function.Consumer;

public interface IAIService {
    void ask(String prompt, Consumer<String> callback);
    String getProviderName();
}
