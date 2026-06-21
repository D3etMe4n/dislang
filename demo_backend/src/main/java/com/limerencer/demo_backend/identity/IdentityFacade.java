package com.limerencer.demo_backend.identity;

public interface IdentityFacade {
    String registerUserFromUsernameAndPassword(String username, String password);
    String syncUserFromToken(String token);
    boolean isUserExists(String username);
}
