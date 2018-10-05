create table if not exists account(
  id bigint unsigned auto_increment primary key,
  balance bigint unsigned
);

create table if not exists balance_history(
  account_from bigint unsigned,
  account_to bigint unsigned,
  amount bigint unsigned,
  time datetime
);
