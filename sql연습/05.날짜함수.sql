--
-- 날짜 함수
--

-- curdate() , current_date
select curdate(), current_date from dual;

-- curtime(), current_time
select curtime(), current_time from dual;

-- now() - 연도 날짜 시간까지 다 나옴 vs sysdate()
select now(), sysdate() from dual;
select now(), sleep(2), now() from dual;
select now(), sleep(2), sysdate() from dual;

-- date_format
-- default format: %Y-%m-%d %h:%i:%s
select date_format(now(), '%Y년 %m월 %d일 %h시 %i분 %s초') from dual;
select date_format(now(), '%d %b') from dual;

-- period_diff
-- 예제) 근무개월
-- 포맷팅 : YYMM
select first_name,
       hire_date,
       period_diff(date_format(curdate(), '%y%m'), date_format(hire_date, '%y%m'))
from employees;

-- date_add(=addate), date_sub(=subdate)
-- 날짜를 date 타입의 컬럼이나 값에 type(year, month, day) 의 표현식으로 더하거나 뺄 수 있다.
-- 예제) 각 사원의 근속 연수가 5년이 되는 날에 휴가를 보내준다면 각 사원의 5년 근속 휴가 날짜는?
select first_name,
       hire_date,
        date_add(hire_date, interval 5 year)
from employees;





