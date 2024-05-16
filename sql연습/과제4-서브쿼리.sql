-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 문제1.
-- 현재 평균 연봉보다 많은 월급을 받는 직원은 몇 명이나 있습니까?

-- 평균 연봉
with avgSal as (select avg(salary) as avg_salary
                from employees a
                         join salaries b on a.emp_no = b.emp_no
                where b.to_date = '9999-01-01')


select count(*)
from employees a
         join salaries b on a.emp_no = b.emp_no
where b.to_date = '9999-01-01'
  and b.salary > (select avg_salary from avgSal);



-- 문제2. (x)
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 연봉을 조회하세요.
-- 단 조회결과는 연봉의 내림차순으로 정렬되어 나타나야 합니다.

-- 그룹 별 최고 연봉
with max_salary as (select max(salary) 최고연봉, c.dept_no 부서
from salaries a
join employees b on a.emp_no = b.emp_no
join dept_emp c on b.emp_no = c.emp_no
where a.to_date = '9999-01-01'
and c.to_date = '9999-01-01'
group by c.dept_no)


select a.emp_no, a.first_name, c.salary
from employees a
join dept_emp b on a.emp_no = b.emp_no
join salaries c on a.emp_no = c.emp_no
join departments d on b.dept_no = d.dept_no
where c.salary = (select 최고연봉 from max_salary where 부서 = b.dept_no)
order by c.salary desc;



-- 문제3.
-- 현재, 자신의 부서 평균 급여보다 연봉(salary)이 많은 사원의 사번, 이름과 연봉을 조회하세요.

-- 부서 평균 급여 구하기
with deptSalAvg as (select a.dept_no, avg(b.salary) as avg_salary
                    from dept_emp a
                             join salaries b on a.emp_no = b.emp_no
                    where a.to_date = '9999-01-01'
                      and b.to_date = '9999-01-01'
                    group by a.dept_no)


select a.emp_no, a.first_name, b.salary
from employees a
         join salaries b on a.emp_no = b.emp_no
         join dept_emp c on a.emp_no = c.emp_no
         join deptSalAvg d on c.dept_no = d.dept_no
where b.to_date = '9999-01-01'
  and b.salary > d.avg_salary;



-- 문제4.
-- 현재, 사원들의 사번, 이름, 매니저 이름, 부서 이름으로 출력해 보세요.

select a.emp_no                          사번,
       a.first_name                      이름,
       (select m.first_name
        from employees m
                 join dept_manager bm on m.emp_no = bm.emp_no
        where bm.dept_no = c.dept_no
          and bm.to_date = '9999-01-01') 매니저이름,
       c.dept_name                       부서이름
from employees a
         join dept_emp b on a.emp_no = b.emp_no
         join departments c on b.dept_no = c.dept_no
where b.to_date = '9999-01-01';



-- 문제5.
-- 현재, 평균연봉이 가장 높은 부서의 사원들의 사번, 이름, 직책, 연봉을 조회하고 연봉 순으로 출력하세요.

-- 평균 연봉이 가장 높은 부서
with highestDept as (select avg(salary) 평균연봉, c.dept_no
                     from employees a
                              join salaries b on a.emp_no = b.emp_no
                              join dept_emp c on a.emp_no = c.emp_no
                     where b.to_date = '9999-01-01'
                       and c.to_date = '9999-01-01'
                     group by c.dept_no
                     order by 평균연봉 desc
                     limit 1)

select a.emp_no 사번, a.first_name 이름, c.title 직책, d.salary 연봉
from employees a
         join dept_emp b on a.emp_no = b.emp_no
         join titles c on a.emp_no = c.emp_no
         join salaries d on a.emp_no = d.emp_no
where b.to_date = '9999-01-01'
  and c.to_date = '9999-01-01'
  and d.to_date = '9999-01-01'
  and b.dept_no = (select dept_no from highestDept)
order by d.salary desc;



-- 문제6.
-- 평균 연봉이 가장 높은 부서는?
select c.dept_no '평균 연봉 높은 부서'
from employees a
         join salaries b on a.emp_no = b.emp_no
         join dept_emp c on a.emp_no = c.emp_no
where b.to_date = '9999-01-01'
  and c.to_date = '9999-01-01'
group by c.dept_no
order by avg(salary) desc
limit 1;



-- 문제7.
-- 평균 연봉이 가장 높은 직책?
select d.title '평균 연봉 높은 직책'
from employees a
         join salaries b on a.emp_no = b.emp_no
         join titles d on a.emp_no = d.emp_no
where b.to_date = '9999-01-01'
  and d.to_date = '9999-01-01'
group by d.title
order by avg(salary) desc
limit 1;


-- 문제8.
-- 현재 자신의 매니저보다 높은 연봉을 받고 있는 직원은?
-- 부서이름, 사원이름, 연봉, 매니저 이름, 메니저 연봉 순으로 출력합니다.

-- 매니저 연봉
with m_salary as (select a.salary as '매니저연봉', dept_no as '부서'
                  from salaries a
                           join employees b on a.emp_no = b.emp_no
                           join dept_manager c on b.emp_no = c.emp_no
                  where a.to_date = '9999-01-01'
                    and c.to_date = '9999-01-01')

# select * from m_salary;

-- 매니저 보다 높은 연봉 받는 직원 조회
select d.dept_name                       부서이름,
       d.dept_no,
       a.first_name                      사원이름,
       c.salary                          연봉,
       (select m.first_name
        from employees m
                 join dept_manager bm on m.emp_no = bm.emp_no
        where b.dept_no = bm.dept_no
          and bm.to_date = '9999-01-01') '매니저 이름',
       (select 매니저연봉
        from m_salary
        where 부서 = b.dept_no)            '매니저 연봉'
from employees a
         join dept_emp b on a.emp_no = b.emp_no
         join salaries c on a.emp_no = c.emp_no
         join departments d on b.dept_no = d.dept_no
where b.to_date = '9999-01-01'
  and c.to_date = '9999-01-01'
  and c.salary > (select 매니저연봉 from m_salary where 부서 = b.dept_no);

