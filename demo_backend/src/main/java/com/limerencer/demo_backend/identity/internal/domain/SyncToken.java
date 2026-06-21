package com.limerencer.demo_backend.identity.internal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "sync_tokens", schema = "identity")
@Getter
@Setter
public class SyncToken {

    @Id
    @Column(name = "token", length = 64, nullable = false)
    private String token;

    @Column(name = "user_id", nullable = false)
    private java.util.UUID userId;

    @Column(name = "expired_at", nullable = false)
    private OffsetDateTime expiredAt;

    @Column(name = "is_used", insertable = false)
    private Boolean isUsed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Identities identity;
}