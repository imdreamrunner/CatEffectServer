# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table manager (
  mid                       integer auto_increment not null,
  username                  varchar(255) not null,
  password                  varchar(255),
  type                      integer,
  sid                       integer,
  constraint uq_manager_username unique (username),
  constraint pk_manager primary key (mid))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table manager;

SET FOREIGN_KEY_CHECKS=1;

