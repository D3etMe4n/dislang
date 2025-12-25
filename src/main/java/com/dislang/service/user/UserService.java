package com.dislang.service.user;

import com.dislang.models.UserProfile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private static final String USER_FILE_PATH = "user.json";
    private final ObjectMapper mapper = new ObjectMapper();

    private final ConcurrentHashMap<String, UserProfile> users =
        new ConcurrentHashMap<>();

    public UserService() {
        loadData();
    }

    public UserProfile getProfile(String userId) {
        return users.computeIfAbsent(userId, k -> {
            UserProfile newProfile = new UserProfile(userId);
            saveData();
            return newProfile;
        });
    }

    public void updateLanguage(String userId, String langCode) {
        UserProfile profile = getProfile(userId);
        profile.setLanguageCode(langCode);
        saveData();
    }

    public void updateModel(String userId, String modelName) {
        UserProfile profile = getProfile(userId);
        profile.setPreferredModel(modelName);
        saveData();
    }

    private void loadData() {
        File file = new File(USER_FILE_PATH);
        if (file.exists()) {
            try {
                ConcurrentHashMap<String, UserProfile> data = mapper.readValue(
                    file,
                    new TypeReference<
                        ConcurrentHashMap<String, UserProfile>
                    >() {}
                );
                users.putAll(data);
                System.out.println(
                    "Loaded " + users.size() + " user profiles."
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveData() {
        try {
            mapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(new File(USER_FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
