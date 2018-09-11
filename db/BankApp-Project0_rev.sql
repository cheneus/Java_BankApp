drop table customer cascade constraints;
drop table address cascade constraints;
drop table employee cascade constraints;
drop table account cascade constraints;
drop table account_type cascade constraints;
drop table account_customer cascade constraints;
drop table transaction cascade constraints;
drop table login_type cascade constraints;
drop table login cascade constraints;

create table address (
  id number primary key,
  lineone varchar2(100) not null,
  linetwo varchar2(100),
  city varchar2(100) not null,
  state varchar2(100) not null,
  zip varchar2(10) not null
);

CREATE TABLE Customer (
    id NUMBER primary key,
    FirstName VARCHAR2(40) NOT NULL,
    LastName VARCHAR2(20) NOT NULL,
    address NUMBER,
    Phone VARCHAR2(24),
    Email VARCHAR2(60) NOT NULL,
    constraint fk_address_customer foreign key (address) references address(id)
);

create table customer_employee (
    id number primary key,
    customer_id number,
    employee_id number,
    constraint fk_cust_ee_customer foreign key (customer_id) references customer(id),
    constraint fk_cust_ee_employee foreign key (employee_id) references employee(id)
)

create table Employee (
    id NUMBER primary key,
    LastName VARCHAR2(20) NOT NULL,
    FirstName VARCHAR2(20) NOT NULL,
    Title VARCHAR2(30),
    ReportsTo NUMBER,
    BirthDate DATE,
    HireDate DATE,
    address number,
    Phone VARCHAR2(24),
    Email VARCHAR2(60) not null,
    constraint fk_address_employee foreign key (address) references address(id)
);

create table Account (
  id number,
  balance number(12,2),
  approved varchar2(32),
  account_type varchar2(32),
  constraint pk_accounts primary key (id)
);

create table Account_Customer (
    id number,
    account_id number,
    customer_id number,
    constraint pk_acc_custId primary key (id),
    constraint fk_account_id foreign key (account_id) references account(id),
    constraint fk_customer_id foreign key (customer_id) references customer(id)
);

create table transaction (
  id number,
  tiemstamp timestamp default systimestamp,
  amount number(12,2),
  type varchar(32),
  account_id number,
  constraint pk_transaction primary key (id),
  constraint fk_trans_acc foreign key (account_id) references account(id)
);

create table login (
  id number(32),
  username varchar2(32) unique not null,
  password varchar2(32) not null,
  customer_id number,
  login_type varchar(32),
  constraint pk_login primary key (id),
  constraint fk_login_cust_id foreign key (customer_id) references customer(id)
);

drop sequence address_seq;
drop sequence customer_seq;
drop sequence employee_seq;
drop sequence account_seq;
drop sequence acc_cust_seq;
drop sequence transaction_seq;
drop sequence login_seq;
drop sequence cust_employ_seq;

create sequence address_seq;
create sequence customer_seq;
create sequence employee_seq;
create sequence cust_employ_seq;
create sequence account_seq;
create sequence acc_cust_seq;
create sequence transaction_seq;
create sequence login_seq;

create or replace trigger address_pk_trig
before insert or update on address
for each row
begin
    if INSERTING then
        select address_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/
create or replace trigger account_pk_trig
before insert or update on account
for each row
begin
    if INSERTING then
        select account_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/
create or replace trigger acc_cust_pk_trig
before insert or update on account_customer
for each row
begin
    if INSERTING then
        select acc_cust_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/
create or replace trigger login_pk_trig
before insert or update on login
for each row
begin
    if INSERTING then
        select login_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/
create or replace trigger login_type_pk_trig
before insert or update on login_type
for each row
begin
    if INSERTING then
        select login_type_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/
create or replace trigger customer_pk_trig
before insert or update on customer
for each row
begin
    if INSERTING then
        select customer_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger employee_pk_trig
before insert or update on employee
for each row
begin
    if INSERTING then
        select employee_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/
create or replace trigger transaction_pk_trig
before insert or update on transaction
for each row
begin
    if INSERTING then
        select transaction_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;

create or replace trigger cust_employee_pk_trig
before insert or update on customer_employee
for each row
begin
    if INSERTING then
        select cust_employ_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/
create or replace procedure getFullCustomer (customer_i  in number, recordset out sys_refcursor)
as
begin
    open recordset for
        select customer.id,  customer.firstname,customer.lastname, customer.email , customer.phone, 
        login.username , login.login_type ,address.lineone , address.linetwo , address.city , address.state, address.zip
        from ( (customer join login on customer.id = login.customer_id) join address on address.id = customer.address )
        where customer.id =customer_i;
end;
/