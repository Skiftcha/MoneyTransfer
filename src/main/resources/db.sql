create table if not exists account(
  id bigint unsigned,
  balance bigint unsigned,
  constraint pk_id primary key (id)
);

create table if not exists balance_history(
  account_from bigint unsigned,
  account_to bigint unsigned,
  amount bigint unsigned,
  time datetime
);

merge into account key(id) values(1, 11);
merge into account key(id) values(2, 13123);
merge into account key(id) values(3, 111);
merge into account key(id) values(4, 322);
