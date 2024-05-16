-- 테이블간 조인(JOIN) SQL 문제입니다.

-- 문제 1.
-- 현재 급여가 많은 직원부터 직원의 사번, 이름, 그리고 연봉을 출력 하시오.
    select a.emp_no 사번, a.first_name 이름, b.salary 연봉
    from employees a join salaries b on a.emp_no = b.emp_no
    where b.to_date = '9999-01-01'
    order by b.salary desc;

-- 문제2.
-- 전체 사원의 사번, 이름, 현재 직책을 이름 순서로 출력하세요.
    select a.emp_no 사번, concat(a.first_name,' ',a.last_name) 이름, b.title 직책
    from employees a join titles b using (emp_no)
    where b.to_date = '9999-01-01'
    order by concat(a.first_name,' ',a.last_name) asc;


-- 문제3.
-- 전체 사원의 사번, 이름, 현재 부서를 이름 순서로 출력하세요..
    select a.emp_no 사번, concat(a.first_name,' ',a.last_name) 이름, c.dept_name 부서
    from employees a, dept_emp b, departments c
    where a.emp_no = b.emp_no
    and b.dept_no = c.dept_no
    order by 이름 asc;


-- 문제4.
-- 현재 사원의 사번, 이름, 연봉, 직책, 부서를 모두 이름 순서로 출력합니다.
    select a.emp_no 사번, concat(a.first_name,' ',a.last_name) 이름, c.salary 연봉, d.title 직책, e.dept_name 부서
    from employees a, dept_emp b, salaries c, titles d, departments e
    where a.emp_no = b.emp_no
    and a.emp_no = c.emp_no
    and a.emp_no = d.emp_no
    and b.dept_no = e.dept_no
    and b.to_date = '9999-01-01'
    and c.to_date = '9999-01-01'
    and d.to_date = '9999-01-01'
    order by 이름 asc;

    select a.emp_no as 사번,
       concat(a.first_name, ' ', a.last_name) as 이름,
       c.salary as 연봉,
       d.title as 직책,
       e.dept_name as 부서,
        count(*)
    from employees a
    join dept_emp b on a.emp_no = b.emp_no
    join salaries c on a.emp_no = c .emp_no
    join titles d on a.emp_no = d.emp_no
    join departments e on b.dept_no = e.dept_no
    where b.to_date = '9999-01-01'
    and c.to_date = '9999-01-01'
    and d.to_date = '9999-01-01'
    order by 이름;


-- 문제5.
-- ‘Technique Leader’의 직책으로 과거에 근무한 적이 있는 모든 사원의 사번과 이름을 출력하세요.
-- (현재 ‘Technique Leader’의 직책(으로 근무하는 사원은 고려하지 않습니다.)  first_name 만
    select a.emp_no, a.first_name
    from employees a
    join titles b on a.emp_no = b.emp_no
    where title = 'Technique Leader'
    and to_date != '9999-01-01';


-- 문제6.
-- 직원 이름(last_name) 중에서 S(대문자)로 시작하는 직원들의 이름, 부서명, 직책을 조회하세요.
    select a.first_name, a.last_name, c.dept_name, d.title
    from employees a
    join dept_emp b on a.emp_no = b.emp_no
    join departments c on b.dept_no = c.dept_no
    join titles d on a.emp_no = d.emp_no
    where a.last_name like 'S%'
    and b.to_date = '9999-01-01'
    and d.to_date = '9999-01-01';

-- 문제7.
-- 현재, 직책이 Engineer인 사원 중에서 현재 급여가 40000 이상인 사원을 급여가 큰 순서대로 출력하세요.
    select a.last_name, c.title, b.salary
    from employees a
    join salaries b on a.emp_no = b.emp_no
    join titles c on a.emp_no = c.emp_no
    where title = 'Engineer'
    and b.to_date = '9999-01-01'
    and c.to_date = '9999-01-01'
    and salary >= 40000
    order by salary desc;

-- 문제8.
-- 현재 급여가 50000이 넘는 직책을 직책, 평균급여로 평균급여가 큰 순서대로 출력하시오
    select c.title 직책, avg(salary) 평균급여
    from employees a
    join salaries b on a.emp_no = b.emp_no
    join titles c on a.emp_no = c.emp_no
    and b.to_date = '9999-01-01'
    and c.to_date = '9999-01-01'
    group by title
    having avg(salary) >= 50000
    order by 평균급여 desc;

-- 문제9.
-- 현재, 부서별 평균 연봉을 연봉이 큰 부서 순서대로 출력하세요.
    select dept_name, avg(salary) 평균연봉
    from employees a
    join salaries b on a.emp_no = b.emp_no
    join dept_emp c on a.emp_no = c.emp_no
    join departments d on c.dept_no = d.dept_no
    where b.to_date = '9999-01-01'
    and c.to_date = '9999-01-01'
    group by dept_name
    order by 평균연봉 desc;




-- 문제10.
-- 현재, 직책별 평균 연봉을 연봉이 큰 직책 순서대로 출력하세요.
    select title, avg(salary) 평균연봉
    from employees a
    join salaries b on a.emp_no = b.emp_no
    join titles c on a.emp_no = c.emp_no
    where b.to_date = '9999-01-01'
    and c.to_date = '9999-01-01'
    group by title
    order by 평균연봉 desc;

