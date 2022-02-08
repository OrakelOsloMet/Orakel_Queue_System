CREATE TABLE placements
(
    id           SERIAL8     NOT NULL,
    created_date TIMESTAMP    NOT NULL,
    name         VARCHAR(255) NOT NULL,
    number       INTEGER      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE subjects
(
    id           SERIAL8     NOT NULL,
    created_date TIMESTAMP    NOT NULL,
    name         VARCHAR(255) NOT NULL,
    semester     VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE queue_entities
(
    id           SERIAL8     NOT NULL,
    created_date TIMESTAMP,
    comment      VARCHAR(255),
    name         VARCHAR(255) NOT NULL,
    study_year   INTEGER      NOT NULL,
    subject_id   BIGINT       NOT NULL,
    placement_id BIGINT       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT qe_fk_placement_id FOREIGN KEY (placement_id) REFERENCES placements (id),
    CONSTRAINT qe_fk_subject_id FOREIGN KEY (subject_id) REFERENCES subjects (id)
);

CREATE TABLE queue_statistics
(
    id           SERIAL8 NOT NULL,
    created_date TIMESTAMP,
    subject_id   BIGINT   NOT NULL,
    placement_id BIGINT   NOT NULL,
    study_year   INTEGER  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT qs_fk_placement_id FOREIGN KEY (placement_id) REFERENCES placements (id),
    CONSTRAINT qs_fk_subject_id FOREIGN KEY (subject_id) REFERENCES subjects (id)
);

CREATE TABLE roles
(
    id   SERIAL8     NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       SERIAL8     NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT ur_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT ur_fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id)
);