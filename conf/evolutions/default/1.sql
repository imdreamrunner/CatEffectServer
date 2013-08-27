# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table login_session (
  lsid                      integer auto_increment not null,
  mid                       integer,
  create_time               datetime,
  refresh_time              datetime,
  auth_code                 varchar(255),
  constraint pk_login_session primary key (lsid))
;

create table manager (
  mid                       integer auto_increment not null,
  username                  varchar(255),
  password                  varchar(255),
  type                      integer,
  sid                       integer,
  constraint pk_manager primary key (mid))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table login_session;

drop table manager;

SET FOREIGN_KEY_CHECKS=1;

