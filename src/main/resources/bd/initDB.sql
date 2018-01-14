CREATE SEQUENCE global_seq
  START WITH 100000;

/
CREATE TABLE CITY
(
  id   NUMBER PRIMARY KEY,
  name VARCHAR2(255)
);

/
COMMENT ON TABLE CITY IS 'Город';


CREATE OR REPLACE TRIGGER TR_PK_ID_CITY
  BEFORE INSERT
  ON CITY
  FOR EACH ROW
  -- Optionally restrict this trigger to fire only when really needed
  WHEN (new.id IS NULL)
  DECLARE
    v_id CITY.id%TYPE;
  BEGIN
    -- Select a new value from the sequence into a local variable. As David
    -- commented, this step is optional. You can directly select into :new.qname_id
    SELECT GLOBAL_SEQ.nextval
    INTO v_id
    FROM DUAL;

    -- :new references the record that you are about to insert into qname. Hence,
    -- you can overwrite the value of :new.qname_id (qname.qname_id) with the value
    -- obtained from your sequence, before inserting
    :new.id := v_id;
  END;
/
CREATE TABLE DEPARTMENT
(
  id   NUMBER PRIMARY KEY,
  name VARCHAR2(255)
);

/
COMMENT ON TABLE DEPARTMENT IS 'Подраделение';

CREATE OR REPLACE TRIGGER TR_PK_ID_DEPARTMENT
  BEFORE INSERT
  ON DEPARTMENT
  FOR EACH ROW
  -- Optionally restrict this trigger to fire only when really needed
  WHEN (new.id IS NULL)
  DECLARE
    v_id DEPARTMENT.id%TYPE;
  BEGIN
    -- Select a new value from the sequence into a local variable. As David
    -- commented, this step is optional. You can directly select into :new.qname_id
    SELECT GLOBAL_SEQ.nextval
    INTO v_id
    FROM DUAL;

    -- :new references the record that you are about to insert into qname. Hence,
    -- you can overwrite the value of :new.qname_id (qname.qname_id) with the value
    -- obtained from your sequence, before inserting
    :new.id := v_id;
  END;
/
CREATE TABLE POSITION
(
  id   NUMBER PRIMARY KEY,
  name VARCHAR2(255)
);

/
COMMENT ON TABLE POSITION IS 'Позиция';

CREATE OR REPLACE TRIGGER TR_PK_ID_POSITION
  BEFORE INSERT
  ON POSITION
  FOR EACH ROW
  -- Optionally restrict this trigger to fire only when really needed
  WHEN (new.id IS NULL)
  DECLARE
    v_id POSITION.id%TYPE;
  BEGIN
    -- Select a new value from the sequence into a local variable. As David
    -- commented, this step is optional. You can directly select into :new.qname_id
    SELECT GLOBAL_SEQ.nextval
    INTO v_id
    FROM DUAL;

    -- :new references the record that you are about to insert into qname. Hence,
    -- you can overwrite the value of :new.qname_id (qname.qname_id) with the value
    -- obtained from your sequence, before inserting
    :new.id := v_id;
  END;
/
CREATE TABLE EMPLOYEE
(
  id            NUMBER PRIMARY KEY,
  login         VARCHAR2(2000) NOT NULL,
  password      VARCHAR2(255)  NOT NULL,
  name          VARCHAR2(2000) NOT NULL,
  department_id NUMBER,
  position_id   NUMBER         NOT NULL,
  city_id       NUMBER         NOT NULL,
  email         VARCHAR2(2000) NOT NULL,
  salary        NUMBER         NOT NULL,
  CONSTRAINT FK_EMPLOYEE_POSITION FOREIGN KEY (position_id) REFERENCES POSITION (ID),
  CONSTRAINT FK_EMPLOYEE_DEPARTMENT FOREIGN KEY (department_id) REFERENCES DEPARTMENT (ID),
  CONSTRAINT FK_EMPLOYEE_CITY FOREIGN KEY (city_id) REFERENCES CITY (ID)
);

/
CREATE UNIQUE INDEX UI_EMPLOYEE_LOGIN
  ON EMPLOYEE (login);

COMMENT ON TABLE EMPLOYEE IS 'Сотрудник';


CREATE OR REPLACE TRIGGER TR_PK_ID_EMPLOYEE
  BEFORE INSERT
  ON EMPLOYEE
  FOR EACH ROW
  -- Optionally restrict this trigger to fire only when really needed
  WHEN (new.id IS NULL)
  DECLARE
    v_id EMPLOYEE.id%TYPE;
  BEGIN
    -- Select a new value from the sequence into a local variable. As David
    -- commented, this step is optional. You can directly select into :new.qname_id
    SELECT GLOBAL_SEQ.nextval
    INTO v_id
    FROM DUAL;

    -- :new references the record that you are about to insert into qname. Hence,
    -- you can overwrite the value of :new.qname_id (qname.qname_id) with the value
    -- obtained from your sequence, before inserting
    :new.id := v_id;
  END;
  /