CREATE SCHEMA IF NOT EXISTS studypet;

CREATE TABLE IF NOT EXISTS developers
(
    ID   BIGINT       NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    account_id BIGINT UNIQUE
)
;

CREATE TABLE IF NOT EXISTS skills
(
    skill_id BIGINT       NOT NULL PRIMARY KEY,
    name     VARCHAR(255) NULL
)
;

CREATE TABLE IF NOT EXISTS `developer-skills`
(
    developer_id BIGINT NOT NULL,
    skill_id     BIGINT NOT NULL,
    PRIMARY KEY (developer_id, skill_id),
    INDEX `devId` (developer_id),
    INDEX `skID` (skill_id),
    CONSTRAINT `fk-skills` FOREIGN KEY (skill_id)
        REFERENCES skills (skill_id) ON DELETE CASCADE,
    CONSTRAINT `fk-devs` FOREIGN KEY (developer_id)
        REFERENCES developers (ID) ON DELETE CASCADE
)
;

CREATE TABLE IF NOT EXISTS accounts
(
    account_id BIGINT,
    # developer_id         BIGINT,
    status     ENUM ('ACTIVE', 'BANNED', 'INACTIVE'),
    CONSTRAINT `fk-dev` FOREIGN KEY (account_id)
        REFERENCES developers (account_id) ON DELETE CASCADE
);
