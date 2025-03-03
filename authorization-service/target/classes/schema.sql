CREATE TABLE users
(
    id                      SERIAL PRIMARY KEY,
    email                   VARCHAR(255) UNIQUE         NOT NULL,
    password                VARCHAR(255)                NOT NULL,
    first_name              VARCHAR(255)                NOT NULL,
    last_name               VARCHAR(255)                NOT NULL,
    profile_picture_url     VARCHAR(255),
    headline                VARCHAR(255),
    summary                 TEXT,
    enabled                 BOOLEAN                     NOT NULL DEFAULT TRUE,
    account_non_expired     BOOLEAN                     NOT NULL DEFAULT TRUE,
    account_non_locked      BOOLEAN                     NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN                     NOT NULL DEFAULT TRUE,
    created_at              TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP WITHOUT TIME ZONE,
    created_by              VARCHAR(255),
    updated_by              VARCHAR(255),
    delete_flg              BOOLEAN                     NOT NULL DEFAULT FALSE
);

CREATE TABLE roles
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE         NOT NULL,
    description TEXT,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    delete_flg  BOOLEAN                     NOT NULL DEFAULT FALSE
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE refresh_tokens
(
    id          SERIAL PRIMARY KEY,
    token       VARCHAR(255) UNIQUE         NOT NULL,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id     BIGINT UNIQUE               NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    delete_flg  BOOLEAN                     NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
