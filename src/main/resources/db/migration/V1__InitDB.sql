CREATE TABLE user
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime NULL,
    last_modified_at datetime NULL,
    name             VARCHAR(255) NULL,
    password         VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);