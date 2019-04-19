# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table borroweddetail (
  borrowid                      integer auto_increment not null,
  isbn                          varchar(255),
  borroweddate                  datetime(6),
  returndate                    datetime(6),
  reader_id                     integer not null,
  settle                        varchar(255),
  overduefee                    varchar(255),
  constraint pk_borroweddetail primary key (borrowid)
);

create table librariabook (
  item_id                       integer auto_increment not null,
  isbn                          varchar(255),
  title                         varchar(255),
  sector                        varchar(255),
  reservation                   integer not null,
  publisher                     varchar(255),
  published_date                date,
  pages                         integer not null,
  author                        varchar(255),
  available                     varchar(255),
  constraint uq_librariabook_isbn unique (isbn),
  constraint pk_librariabook primary key (item_id)
);

create table librariadvd (
  item_id                       integer auto_increment not null,
  isbn                          varchar(255),
  title                         varchar(255),
  sector                        varchar(255),
  reservation                   integer not null,
  producer                      varchar(255),
  actor                         varchar(255),
  publishdate                   date,
  languages                     varchar(255),
  subtitles                     varchar(255),
  borrowed                      varchar(255),
  constraint uq_librariadvd_isbn unique (isbn),
  constraint pk_librariadvd primary key (item_id)
);

create table reader (
  readerid                      integer auto_increment not null,
  name                          varchar(255),
  mobileno                      varchar(255),
  email                         varchar(255),
  readerstatus                  varchar(255),
  constraint uq_reader_email unique (email),
  constraint pk_reader primary key (readerid)
);


# --- !Downs

drop table if exists borroweddetail;

drop table if exists librariabook;

drop table if exists librariadvd;

drop table if exists reader;

