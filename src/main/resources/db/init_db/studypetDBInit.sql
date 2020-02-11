create schema if not exists studypet;

create table if not exists studypet.developers
(
    id   bigint       not null primary key,
    name varchar(255) not null,
    account_id bigint unique
)
;

create table if not exists studypet.skills
(
    skill_id bigint       not null primary key,
    name     varchar(255) null
)
;

create table if not exists studypet.developer_skills
(
    developer_id bigint not null,
    skill_id     bigint not null,
    primary key (developer_id, skill_id),
    constraint `fk-skills` foreign key (skill_id)
        references skills (skill_id) on delete cascade,
    constraint `fk-devs` foreign key (developer_id)
        references developers (id) on delete cascade
)
;

create table if not exists studypet.accounts
(
    account_id bigint,
    status     enum ('ACTIVE', 'BANNED', 'INACTIVE'),
    constraint `fk-dev` foreign key (account_id)
        references developers (account_id) on delete cascade
);
