create table if not exists accounts
(
    account_id bigint,
    status     enum ('ACTIVE', 'BANNED', 'INACTIVE')
);
