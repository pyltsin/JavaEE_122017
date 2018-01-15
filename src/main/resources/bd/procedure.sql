CREATE OR REPLACE PROCEDURE TESTOUT(DT OUT VARCHAR2)
IS

  BEGIN
    SELECT name
    INTO DT
    FROM (
           SELECT
             name,
             rownum AS n
           FROM EMPLOYEE
           ORDER BY SALARY DESC) vv
    WHERE n = 1;

  END TESTOUT;
/