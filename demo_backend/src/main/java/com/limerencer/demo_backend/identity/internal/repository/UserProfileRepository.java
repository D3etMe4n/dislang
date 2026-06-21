package com.limerencer.demo_backend.identity.internal.repository;

import com.limerencer.demo_backend.identity.internal.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    //TODO: write this when the web has profile editing feature.
}
