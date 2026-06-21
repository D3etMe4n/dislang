CREATE TABLE identity.identities (
                                     id UUID PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
                                     password_hash VARCHAR(255) NOT NULL,
                                     discord_id VARCHAR(30) UNIQUE,
                                     discord_username VARCHAR(100),
                                     roles VARCHAR(50)[] DEFAULT '{"USER"}',
                                     created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE identity.user_profiles (
                                        user_id UUID PRIMARY KEY REFERENCES identity.identities(id) ON DELETE CASCADE,
                                        email VARCHAR(100) UNIQUE,
                                        full_name VARCHAR(100),
                                        avatar_url VARCHAR(255),
                                        updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE identity.sync_tokens (
                                      token VARCHAR(64) PRIMARY KEY,
                                      user_id UUID NOT NULL REFERENCES identity.identities(id) ON DELETE CASCADE,
                                      expired_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                      is_used BOOLEAN DEFAULT FALSE
);

CREATE INDEX idx_identities_discord_id ON identity.identities(discord_id);
CREATE INDEX idx_identities_username ON identity.identities(username);