CREATE TABLE placements
(
    id           IDENTITY     NOT NULL,
    created_date TIMESTAMP    NOT NULL,
    name         VARCHAR(255) NOT NULL,
    number       INTEGER      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE subjects
(
    id           IDENTITY     NOT NULL,
    created_date TIMESTAMP    NOT NULL,
    name         VARCHAR(255) NOT NULL,
    semester     VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE queue_entities
(
    id           IDENTITY     NOT NULL,
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
    id           IDENTITY NOT NULL,
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
    id   IDENTITY     NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       IDENTITY     NOT NULL,
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

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO subjects (name, semester, created_date) VALUES ('Programmering', 'AUTUMN', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Diskret Matte', 'AUTUMN', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Prototyping', 'AUTUMN', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Algoritmer og Datastrukturer', 'AUTUMN', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Matte 2000', 'AUTUMN', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Sytemutvikling', 'AUTUMN', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('MMI', 'AUTUMN', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Webutvikling', 'AUTUMN', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Webapplikasjoner', 'AUTUMN', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Apputvikling', 'AUTUMN', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Databaser', 'SPRING', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Internet of Things', 'SPRING', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Matte 1000', 'SPRING', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Visualisering', 'SPRING', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Testing av Programvare', 'SPRING', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Fysikk og Kjemi', 'SPRING', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Datanettverk og Skytjenester', 'SPRING', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Operativsystemer', 'SPRING', '2022-02-08 14:00:00.0');
INSERT INTO subjects (name, semester, created_date) VALUES ('Webprogrammering', 'SPRING', '2022-02-08 14:00:00.0');