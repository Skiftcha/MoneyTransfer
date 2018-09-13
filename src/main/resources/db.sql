create table account(
  id bigint unsigned,
  balance bigint unsigned,
  constraint pk_id primary key (id)
);

insert into account values(1, 11);
insert into account values(2, 13123);
insert into account values(3, 111);
insert into account values(4, 322);
