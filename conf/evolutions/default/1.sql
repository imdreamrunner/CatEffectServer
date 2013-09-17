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
  stall_id                  integer,
  category_id               integer,
  sort                      integer,
  list_price                integer,
  price                     integer,
  allow_discount            tinyint(1) default 0,
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
  original_subtotal         integer,
  discount                  integer,
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
  discount                  integer,
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

alter table manager add constraint fk_manager_stall_1 foreign key (stall_id) references stall (stall_id) on delete restrict on update restrict;
create index ix_manager_stall_1 on manager (stall_id);



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

