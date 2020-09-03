create table member(
idx number(11) primary key,
name varchar2(50) not null,
email varchar2(50) not null,
passwd varchar2(255) not null,
phone varchar2(13) not null,
created timestamp not null,
updated timestamp not null);


create table notice(
idx number(11) primary key,
userIdx number(11) not null,
subject nvarchar2(1000) not null,
content long not null,
viewCount number(11) not null,
created timestamp not null,
updated timestamp not null);

create sequence idx_seq start with 1 increment by 1 maxvalue 9999;