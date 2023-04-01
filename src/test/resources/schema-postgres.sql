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

