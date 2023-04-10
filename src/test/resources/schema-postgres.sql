create schema IF NOT EXISTS coding_toast;

drop table if exists coding_toast.address;
create table coding_toast.address
(
    city_name varchar(50) not null,
    zipcode   varchar(50) not null,
    geom geometry(Point, 5186)
);

drop table if exists coding_toast."user";
create table coding_toast."user"
(
    id           serial
        constraint user_pk
            primary key,
    name         varchar(50) not null,
    phone_number varchar(50)
);

drop table if exists coding_toast."test_insert_table";
create table coding_toast."test_insert_table" (
    id int not null,
    name    varchar(50) not null,
    constraint test_insert_table_pk primary key (id)
);

DROP SEQUENCE IF EXISTS CODING_TOAST.TEST_INSERT_SEQUENCE;
CREATE SEQUENCE CODING_TOAST.TEST_INSERT_SEQUENCE START 200;
