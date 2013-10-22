# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  account_id                integer not null,
  type                      integer,
  relevant_id               integer,
  balance                   integer,
  create_time               timestamp,
  last_used_time            timestamp,
  constraint pk_account primary key (account_id))
;

create table canteen (
  canteen_id                integer not null,
  name                      varchar(255),
  sort                      integer,
  constraint pk_canteen primary key (canteen_id))
;

create table category (
  category_id               integer not null,
  name                      varchar(255),
  stall_id                  integer,
  display_option            integer,
  sort                      integer,
  constraint pk_category primary key (category_id))
;

create table dish (
  dish_id                   integer not null,
  name                      varchar(255),
  image                     varchar(255),
  description               clob,
  category_id               integer,
  sort                      integer,
  price                     integer,
  promotional_price         integer,
  promotion_start           integer,
  promotion_end             integer,
  options                   varchar(255),
  constraint pk_dish primary key (dish_id))
;

create table manager (
  manager_id                integer not null,
  username                  varchar(255) not null,
  password                  varchar(255),
  type                      integer,
  stall_id                  integer,
  constraint uq_manager_username unique (username),
  constraint pk_manager primary key (manager_id))
;

create table order_detail (
  order_id                  integer not null,
  account_id                integer,
  subtotal                  integer,
  transaction_id            integer,
  status                    integer,
  create_time               timestamp,
  serve_time                timestamp,
  feedback1                 integer,
  feedback2                 integer,
  feedback3                 integer,
  constraint pk_order_detail primary key (order_id))
;

create table order_item (
  order_item_id             integer not null,
  order_id                  integer,
  dish_id                   integer,
  list_price                integer,
  price                     integer,
  quantity                  integer,
  note                      varchar(255),
  constraint pk_order_item primary key (order_item_id))
;

create table prepaid_card (
  prepaid_card_id           integer not null,
  token                     varchar(255),
  constraint pk_prepaid_card primary key (prepaid_card_id))
;

create table stall (
  stall_id                  integer not null,
  name                      varchar(255),
  sort                      integer,
  description               clob,
  image                     varchar(255),
  canteen_id                integer,
  prepaid_discount          integer,
  student_discount          integer,
  facuty_discount           integer,
  stall_discount            integer,
  constraint pk_stall primary key (stall_id))
;

create table transaction (
  transaction_id            integer not null,
  account_id                integer,
  type                      integer,
  amount                    integer,
  time                      timestamp,
  manager_id                integer,
  constraint pk_transaction primary key (transaction_id))
;

create sequence account_seq;

create sequence canteen_seq;

create sequence category_seq;

create sequence dish_seq;

create sequence manager_seq;

create sequence order_detail_seq;

create sequence order_item_seq;

create sequence prepaid_card_seq;

create sequence stall_seq;

create sequence transaction_seq;

alter table category add constraint fk_category_stall_1 foreign key (stall_id) references stall (stall_id) on delete restrict on update restrict;
create index ix_category_stall_1 on category (stall_id);
alter table dish add constraint fk_dish_category_2 foreign key (category_id) references category (category_id) on delete restrict on update restrict;
create index ix_dish_category_2 on dish (category_id);
alter table manager add constraint fk_manager_stall_3 foreign key (stall_id) references stall (stall_id) on delete restrict on update restrict;
create index ix_manager_stall_3 on manager (stall_id);
alter table order_detail add constraint fk_order_detail_account_4 foreign key (account_id) references account (account_id) on delete restrict on update restrict;
create index ix_order_detail_account_4 on order_detail (account_id);
alter table order_detail add constraint fk_order_detail_transaction_5 foreign key (transaction_id) references transaction (transaction_id) on delete restrict on update restrict;
create index ix_order_detail_transaction_5 on order_detail (transaction_id);
alter table order_item add constraint fk_order_item_order_6 foreign key (order_id) references order_detail (order_id) on delete restrict on update restrict;
create index ix_order_item_order_6 on order_item (order_id);
alter table order_item add constraint fk_order_item_dish_7 foreign key (dish_id) references dish (dish_id) on delete restrict on update restrict;
create index ix_order_item_dish_7 on order_item (dish_id);
alter table stall add constraint fk_stall_canteen_8 foreign key (canteen_id) references canteen (canteen_id) on delete restrict on update restrict;
create index ix_stall_canteen_8 on stall (canteen_id);
alter table transaction add constraint fk_transaction_account_9 foreign key (account_id) references account (account_id) on delete restrict on update restrict;
create index ix_transaction_account_9 on transaction (account_id);
alter table transaction add constraint fk_transaction_manager_10 foreign key (manager_id) references manager (manager_id) on delete restrict on update restrict;
create index ix_transaction_manager_10 on transaction (manager_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists account;

drop table if exists canteen;

drop table if exists category;

drop table if exists dish;

drop table if exists manager;

drop table if exists order_detail;

drop table if exists order_item;

drop table if exists prepaid_card;

drop table if exists stall;

drop table if exists transaction;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists account_seq;

drop sequence if exists canteen_seq;

drop sequence if exists category_seq;

drop sequence if exists dish_seq;

drop sequence if exists manager_seq;

drop sequence if exists order_detail_seq;

drop sequence if exists order_item_seq;

drop sequence if exists prepaid_card_seq;

drop sequence if exists stall_seq;

drop sequence if exists transaction_seq;

