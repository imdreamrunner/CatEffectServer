# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table faculty (
  faculty_id                integer auto_increment not null,
  name                      varchar(255),
  email                     varchar(255),
  card_id                   varchar(255),
  constraint pk_faculty primary key (faculty_id))
;

create table staff (
  staff_id                  integer auto_increment not null,
  name                      varchar(255),
  email                     varchar(255),
  card_id                   varchar(255),
  constraint pk_staff primary key (staff_id))
;

create table student (
  student_id                integer auto_increment not null,
  name                      varchar(255),
  email                     varchar(255),
  card_id                   varchar(255),
  constraint pk_student primary key (student_id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table faculty;

drop table staff;

drop table student;

SET FOREIGN_KEY_CHECKS=1;

