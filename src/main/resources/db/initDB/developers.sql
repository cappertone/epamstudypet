create table if not exists developers
(
    id   bigint       not null primary key,
    name varchar(255) not null,
    account_id bigint unique
);