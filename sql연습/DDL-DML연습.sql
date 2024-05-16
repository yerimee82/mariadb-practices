-- DDL/DML 연습
use webdb;
drop table member;
create table member
(
    no         int          not null auto_increment,
    email      varchar(200) not null default '',
    password   varchar(64)  not null,
    name       varchar(50)  not null,
    department varchar(100),
    primary key (no)
);

desc member;

alter table member
    add column juminbunho char(13) not null;

alter table member
    drop column juminbunho;

alter table member
    add column juminbunho char(13) not null after email;

alter table member
    change column department dept varchar(100) not null;
desc member;

alter table member
    add column self_intro text;

alter table member
    drop juminbunho;
desc member;

-- insert
insert into member(no, email, name, dept, password)
values (null, 'hello@gmail.com', '안녕히', '기획팀', password ('1234'));

insert
into member
values (null, 'yerim@gmail.com', password('1234'), '김예림', '개발팀', null);

select *
from member;


-- update
update member
set email='yerim@gmail.com', password=password('4321')
where no = 1;

-- delete
delete from member where no=5;
select * from member;


-- transaction
select no, email from member;


select @@autocommit;  -- 1
insert
into member
values (null, 'yerim4@gmail.com', password('1234'), '김예림4', '개발팀', null);


-- tx begin
set autocommit = 0;
select @@autocommit;
