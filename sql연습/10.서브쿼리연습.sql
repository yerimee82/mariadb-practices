--
-- subquery
--

--
-- 1) select 절, insert into t1 values(...)
--


--
-- 2) from 절의 서브쿼리
--
select now() as n, sysdate() as s, 3 + 1 as r
from dual; -- 어떤 select의 결과를 하나의 테이블로 볼 수 있다.

select n, s
from (select now() as n, sysdate() as s, 3 + 1 as r from dual) a;

--
-- 3) where 절의 서브쿼리
--

-- 예제) 현재, Fai Bale이 근무하는 부서에서 근무하는 다른 직원의 사번과 이름을 출력하세요.
select *
from employees a,
     dept_emp b
where a.emp_no = b.emp_no
  and b.to_date = '9999-01-01'
  and concat(a.first_name, ' ', a.last_name) = 'Fai Bale';

-- 'd004'

select a.emp_no, a.first_name
from employees a,
     dept_emp b
where a.emp_no = b.emp_no
  and b.to_date = '9999-01-01'
  and b.dept_no = 'd004';

-- 서브 쿼리 : 단일 값,행 서브쿼리일 시 '=' 사용 가능, 다중 행,컬럼 서브쿼리일 시 in, any, all 연산자 사용해야 함.

select a.emp_no, a.first_name
from employees a,
     dept_emp b
where a.emp_no = b.emp_no
  and b.to_date = '9999-01-01'
  and b.dept_no = (select b.dept_no
                   from employees a,
                        dept_emp b
                   where a.emp_no = b.emp_no
                     and b.to_date = '9999-01-01'
                     and concat(a.first_name, ' ', a.last_name) = 'Fai Bale');


-- 3-1) 단일 행 연산자: =, >, <, >=, <=, <>, !=






-- 3-2) 복수 행 연산자: in, not in, (비교연산자)any, (비교연산자)all