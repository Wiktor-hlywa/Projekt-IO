CREATE TABLE app_users (
                           id            BIGSERIAL PRIMARY KEY,
                           nickname      VARCHAR(50)  NOT NULL UNIQUE,
                           first_name    VARCHAR(100),
                           last_name     VARCHAR(100),
                           password_hash VARCHAR(255) NOT NULL,
                           role          VARCHAR(20)  NOT NULL
                               CHECK (role IN ('ADMIN', 'ORGANIZER', 'PLAYER', 'OBSERVER'))
);

CREATE TABLE teams (
                       id         BIGSERIAL PRIMARY KEY,
                       name       VARCHAR(100) NOT NULL UNIQUE,
                       captain_id BIGINT REFERENCES app_users(id)
);

CREATE TABLE team_members (
                              team_id BIGINT NOT NULL REFERENCES teams(id) ON DELETE CASCADE,
                              user_id BIGINT NOT NULL REFERENCES app_users(id),
                              PRIMARY KEY (team_id, user_id)
);

CREATE TABLE tournaments (
                             id               BIGSERIAL PRIMARY KEY,
                             name             VARCHAR(255) NOT NULL,
                             start_date       DATE,
                             location         VARCHAR(255),
                             organizer_id     BIGINT NOT NULL REFERENCES app_users(id),
                             type             VARCHAR(20) NOT NULL
                                 CHECK (type IN ('INDIVIDUAL', 'TEAM')),
                             status           VARCHAR(20) NOT NULL DEFAULT 'DRAFT'
                                 CHECK (status IN ('DRAFT', 'REGISTRATION', 'IN_PROGRESS', 'FINISHED')),
                             max_participants INT NOT NULL CHECK (max_participants >= 2)
);

CREATE TABLE participants (
                              id            BIGSERIAL PRIMARY KEY,
                              tournament_id BIGINT NOT NULL REFERENCES tournaments(id) ON DELETE CASCADE,
                              user_id       BIGINT REFERENCES app_users(id),
                              team_id       BIGINT REFERENCES teams(id),
    -- dokładnie jedno z dwóch: albo gracz, albo drużyna
                              CHECK ((user_id IS NULL) <> (team_id IS NULL)),
                              UNIQUE (tournament_id, user_id),
                              UNIQUE (tournament_id, team_id)
);

CREATE TABLE matches (
                         id              BIGSERIAL PRIMARY KEY,
                         tournament_id   BIGINT NOT NULL REFERENCES tournaments(id) ON DELETE CASCADE,
                         round           INT NOT NULL CHECK (round >= 1),
                         position        INT NOT NULL CHECK (position >= 0),
                         participant1_id BIGINT REFERENCES participants(id),
                         participant2_id BIGINT REFERENCES participants(id),
                         winner_id       BIGINT REFERENCES participants(id),
                         UNIQUE (tournament_id, round, position)
);

-- blokuje dostęp do tabel przez API Supabase; Spring (właściciel tabel) działa normalnie
ALTER TABLE app_users    ENABLE ROW LEVEL SECURITY;
ALTER TABLE teams        ENABLE ROW LEVEL SECURITY;
ALTER TABLE team_members ENABLE ROW LEVEL SECURITY;
ALTER TABLE tournaments  ENABLE ROW LEVEL SECURITY;
ALTER TABLE participants ENABLE ROW LEVEL SECURITY;
ALTER TABLE matches      ENABLE ROW LEVEL SECURITY;