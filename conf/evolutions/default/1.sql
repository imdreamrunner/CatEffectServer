# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  account_id                integer auto_increment not null,
  type                      integer,
  relevant_id               integer,
  balance                   integer,
  create_time               datetime,
  last_used_time            datetime,
  constraint pk_account primary key (account_id))
;

create table canteen (
  canteen_id                integer auto_increment not null,
  name                      varchar(255),
  sort                      integer,
  constraint pk_canteen primary key (canteen_id))
;

create table category (
  category_id               integer auto_increment not null,
  name                      varchar(255),
  stall_id                  integer,
  display_option            integer,
  sort                      integer,
  constraint pk_category primary key (category_id))
;

create table dish (
  dish_id                   integer auto_increment not null,
  name                      varchar(255),
  image                     varchar(255),
  description               longtext,
  category_id               integer,
  sort                      integer,
  list_price                integer,
  price                     integer,
  options                   varchar(255),
  constraint pk_dish primary key (dish_id))
;

create table manager (
  manager_id                integer auto_increment not null,
  username                  varchar(255) not null,
  password                  varchar(255),
  type                      integer,
  stall_id                  integer,
  constraint uq_manager_username unique (username),
  constraint pk_manager primary key (manager_id))
;

create table order_detail (
  order_id                  integer auto_increment not null,
  account_id                integer,
  subtotal                  integer,
  transaction_id            integer,
  status                    integer,
  create_time               datetime,
  serve_time                datetime,
  constraint pk_order_detail primary key (order_id))
;

create table order_item (
  order_item_id             integer auto_increment not null,
  order_id                  integer,
  dish_id                   integer,
  list_price                integer,
  price                     integer,
  quantity                  integer,
  note                      varchar(255),
  constraint pk_order_item primary key (order_item_id))
;

create table stall (
  stall_id                  integer auto_increment not null,
  name                      varchar(255),
  sort                      integer,
  description               longtext,
  image                     varchar(255),
  canteen_id                integer,
  constraint pk_stall primary key (stall_id))
;

create table transaction (
  transaction_id            integer auto_increment not null,
  account_id                integer,
  type                      integer,
  amount                    integer,
  time                      datetime,
  manager_id                integer,
  constraint pk_transaction primary key (transaction_id))
;

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

SET FOREIGN_KEY_CHECKS=0;

drop table account;

drop table canteen;

drop table category;

drop table dish;

drop table manager;

drop table order_detail;

drop table order_item;

drop table stall;

drop table transaction;

SET FOREIGN_KEY_CHECKS=1;

