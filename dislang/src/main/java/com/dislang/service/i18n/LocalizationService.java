package com.dislang.service.i18n;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class LocalizationService {

    private static final Map<String, JsonNode> langs = new HashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        load("vi");
        load("en");
        load("jp");
    }

    private static void load(String code) {
        try (
            InputStream is = LocalizationService.class.getResourceAsStream(
                "/lang/" + code + ".json"
            )
        ) {
            if (is != null) {
                langs.put(code, mapper.readTree(is));
                System.out.println("Loaded lang: " + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String lang, String key) {
        JsonNode node = langs.getOrDefault(lang, langs.get("en"));
        if (node == null) return key;

        String[] parts = key.split("\\.");
        for (String part : parts) {
            if (node.has(part)) node = node.get(part);
            else return key;
        }
        return node.asText();
    }
}
