package com.limerencer.demo_backend.identity.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface IdentitiesRepository extends JpaRepository<com.limerencer.demo_backend.identity.internal.domain.Identities, UUID> {
    boolean existsByUsername(String username);
    boolean existsByDiscordId(String discordId);
    Optional<IdentitiesRepository> findByUsername(String username);
    Optional<IdentitiesRepository> findByDiscordId(String discordId);
}
