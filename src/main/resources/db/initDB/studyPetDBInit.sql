create schema if not exists studypet;

CREATE TABLE IF NOT EXISTS studypet.developers
(
    ID   BIGINT       NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    account_id BIGINT UNIQUE
)
;

CREATE TABLE IF NOT EXISTS studypet.skills
(
    skill_id BIGINT       NOT NULL PRIMARY KEY,
    name     VARCHAR(255) NULL
)
;

CREATE TABLE IF NOT EXISTS studypet.developer_skills
(
    developer_id BIGINT NOT NULL,
    skill_id     BIGINT NOT NULL,
    PRIMARY KEY (developer_id, skill_id),
    CONSTRAINT `fk-skills` FOREIGN KEY (skill_id)
        REFERENCES skills (skill_id) ON DELETE CASCADE,
    CONSTRAINT `fk-devs` FOREIGN KEY (developer_id)
        REFERENCES developers (ID) ON DELETE CASCADE
)
;

create table if not exists studypet.accounts
(
    account_id bigint,
    status     enum ('ACTIVE', 'BANNED', 'INACTIVE'),
    constraint `fk-dev` foreign key (account_id)
        references developers (account_id) on delete cascade
);
