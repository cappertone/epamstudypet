alter table developer_skills
    add constraint `fk-skills` foreign key (skill_id)
        references skills (skill_id)
        on delete cascade,
    add constraint `fk-devs` foreign key (developer_id)
        references developers (id)
        on delete cascade;