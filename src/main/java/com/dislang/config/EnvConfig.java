package com.dislang.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {

    private static final Dotenv DOTENV = Dotenv.configure()
        .ignoreIfMissing()
        .load();

    public static String get(String key) {
        String value = System.getenv(key);
        return value;
    }
}
