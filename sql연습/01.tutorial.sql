select version(), current_date, now() from dual;

-- 수학함수, 사칙연산도 된다.
select sin(pi()/4), 1 + 2 * 3 - 4 / 5 from dual;

-- 대소문자 구분이 없다.
sELecT VERSION(), current_DATE, NOW() From DUAL;

-- table 생성: DDL
CREATE TABLE pet (
                     name VARCHAR(100),
                     owner VARCHAR(50),
                     species VARCHAR(20),
                     gender CHAR(1),
                     birth DATE,
                     death DATE
);

-- schema 확인
describe pet;
desc pet;

-- table 삭제
drop table pet;
show tables;

-- insert: DML(C)
insert
into pet
values ('성탄이', '안대혁', 'dog', 'm', '2007-12-25', null);

-- select: DML(R)
select * from pet;

-- update: DML(U)
update pet set name='성타니' where name='성탄이';

-- delete: DML(D)
delete from pet where name='성타니';

-- load data: mysql(CLI) 전용
load data local infile '/root/pet.txt' into table pet;

-- select 연습
select name, species
from pet
where name = 'bowser';

select name, species, birth
from pet
where birth >= '1998-01-10';

select name, species, gender
from pet
where species = 'dog'
  and gender = 'f';

select name, species
from pet
where species = 'bird'
   or species = 'snake';

select name, birth
from pet
order by birth asc;

select name, birth
from pet
order by birth desc;

select name, birth, death
from pet
where death is not null;

select name
from pet
where name like 'b%';

select name
from pet
where name like '%fy';

select name
from pet
where name like '%w%';

select name
from pet
where name like '_____';

select name
from pet
where name like 'b____';

select count(*), max(birth)
from pet;