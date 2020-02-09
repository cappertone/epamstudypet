create table if not exists developer_skills
(
    developer_id bigint not null,
    skill_id     bigint not null,
    primary key (developer_id, skill_id)
)
;