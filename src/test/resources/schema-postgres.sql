create schema IF NOT EXISTS coding_toast;

drop table if exists coding_toast.address;
create table coding_toast.address
(
    city_name varchar(50) not null,
    zipcode   varchar(50) not null,
    geom geometry(Point, 5186)
);

drop table if exists coding_toast.users;
create table coding_toast.users
(
    id           serial
        constraint user_pk
            primary key,
    name         varchar(50) not null,
    phone_number varchar(50)
);


DROP SEQUENCE IF EXISTS CODING_TOAST.TEST_INSERT_SEQUENCE;
CREATE SEQUENCE CODING_TOAST.TEST_INSERT_SEQUENCE START 200;


drop table if exists coding_toast.test_insert_table;
create table coding_toast.test_insert_table (
    id int not null,
    name    varchar(50) not null,
    constraint test_insert_table_pk primary key (id)
);


drop table if exists coding_toast.null_insert_table;
DROP SEQUENCE IF EXISTS coding_toast.null_insert_table_sequence;
CREATE SEQUENCE coding_toast.null_insert_table_sequence;
create table coding_toast.null_insert_table
(
    id   bigint not null default nextval('coding_toast.null_insert_table_sequence'),
    name varchar(50),
    age  integer,
    constraint null_insert_table_pk primary key (id)
);
