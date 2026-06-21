package com.limerencer.demo_backend.identity.internal.repository;

import com.limerencer.demo_backend.identity.internal.domain.SyncToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SyncTokenRepository extends JpaRepository<SyncToken, String> {
    Optional<SyncToken> findByTokenAndIsUsedFalse(String token);
}
