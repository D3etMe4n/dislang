package com.limerencer.demo_backend.identity.internal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "identities", schema = "identity")
@Getter
@Setter
public class Identities {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String password_hash;

    @Column(name = "discord_id", unique = true, length = 30)
    private String discordId;

    @Column(name = "discord_username", length = 100)
    private String discordUsername;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "identity_roles",
            joinColumns = @JoinColumn(name = "identity_id")
    )
    @Column(name = "role")
    private List<String> roles = new ArrayList<>(List.of("USER"));

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}
