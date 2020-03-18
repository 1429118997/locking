/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/3/16 21:58:32                           */
/*==============================================================*/


drop table if exists redpacted;

drop table if exists user_redpacked;

/*==============================================================*/
/* Table: redpacted                                             */
/*==============================================================*/
create table redpacted
(
   red_packed_id        int(12) not null auto_increment,
   user_id              int(12),
   amount               decimal(16,2),
   send_time            timestamp,
   total                int(12),
   stock                int(12),
   unit_amount          decimal(16,2),
   version              int(12),
   note                 varchar(256),
   primary key (red_packed_id)
);

/*==============================================================*/
/* Table: user_redpacked                                        */
/*==============================================================*/
create table user_redpacked
(
   id                   int(12) not null auto_increment,
   user_id              int(12),
   red_packed           int(12),
   amount               decimal(16,2),
   grab_time            timestamp,
   noto                 varchar(256),
   primary key (id)
);

