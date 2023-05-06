create schema IF NOT EXISTS coding_toast;

drop table if exists coding_toast.address;
create table if not exists coding_toast.address
(
    city_name varchar(50) not null,
    zipcode   varchar(50) not null
);

drop table if exists coding_toast.users;
create table if not exists  coding_toast.users
(
    id           serial
        constraint user_pk
            primary key,
    name         varchar(50) not null,
    phone_number varchar(50)
);

