select version(), current_date from dual;

-- 수학 함수, 사칙 연산도 된다.
select sign(pi()/4), 1+2 * 3- 4 / 5 from dual;

-- 대소문자 구분이 없다.
select VERSION(), current_date, NOW() from dual;

USE webdb;

-- table 생성: DDL
create table pet(
    name    varchar(100),
    owner   varchar(50),
    species varchar(20),
    gender  char(1),
    birth   date,
    death   date
);

-- schema 확인
describe pet;
desc pet;

-- table 삭제
drop table pet;
show tables;

-- insert: DML(C)
insert into pet values('성탄이', '안대혁', 'dog', 'm', '2007-12-25', null);

-- select: DML(R)
select * from pet;

-- update: DML(U)
update pet set name ='성타니' where name='성탄이';

-- delete: DML(D)
delete from pet where name='성타니';

-- load data: mysql(CLI) 전용
load data local infile '/root/pet.txt' into table pet;

-- select 연습
select name, species, birth
    from pet
where birth >= '1998-01-01';


select name, species, gender
    from pet
where species = 'dog'
    and gender = 'f';

select name, species, gender
    from pet
where species = 'bird'
    or gender = 'snake';

select name, birth
    from pet
order by birth desc;


select name, birth
    from pet
where name like 'b%';

select name, birth
    from pet
where name like 'fy%';

select name, birth
    from pet
where name like '%w%';

# 이름이 4자인
select name, birth
    from pet
where name like '____';

select count(name)
from pet;