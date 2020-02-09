alter table accounts
add constraint fk_dev foreign key (account_id) references developers (account_id)
  on delete cascade;