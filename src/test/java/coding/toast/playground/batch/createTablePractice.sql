create table if not exists coding_toast.address
(
    city_name varchar(50) not null,
    zipcode   varchar(50) not null,
    geom      geometry(Point, 5186)
);

insert into coding_toast.address (city_name, zipcode, geom)
values  ('New York', '00000', '010100002042140000ADBF250025CF0D419FC7288FD7A81D41'),
        ('California', '11111', '010100002042140000ADBF250025CF0D419FC7288FD7A81D41'),
        ('Texas', '22222', '010100002042140000ADBF250025CF0D419FC7288FD7A81D41'),
        ('Arizona', '33333', '010100002042140000ADBF250025CF0D419FC7288FD7A81D41'),
        ('Indiana', '44444', '010100002042140000ADBF250025CF0D419FC7288FD7A81D41'),
        ('Washington', '55555', '010100002042140000ADBF250025CF0D419FC7288FD7A81D41'),
        ('Oklahoma', '66666', '010100002042140000ADBF250025CF0D419FC7288FD7A81D41');


create table if not exists  coding_toast."user"
(
    id           serial
        constraint user_pk
            primary key,
    name         varchar(50) not null,
    phone_number varchar(50)
);

insert into coding_toast.user (id, name, phone_number)
values  (default, 'sam park', '010-0000-0001'),
        (default, 'don joe', '010-0002-0003'),
        (default, 'tom millar', '010-0004-0005'),
        (default, 'Good mand', '010-0006-0007'),
        (default, 'sam park', '010-0000-0001');



---------------------------------------------------------------















DO
$$
    BEGIN

        drop table if exists coding_toast.user_test;

        -- create table
        create table coding_toast.user_test
        (
            id    int,
            attr1 character varying(20),
            attr2 character varying(20),
            attr3 character varying(20),
            attr4 character varying(20),
            geom geometry(multipolygon, 5186),
            index geom_ss
        );



        -- config primary key
        alter table coding_toast.user_test
            add constraint pk_for_coding_toast PRIMARY KEY (id);

        -- insert into coding_toast.user_test(id, attr1, attr2, attr3, attr4)
        -- values (1,2,3,4,5),
        --        (2,2,3,4,5),
        --        (3,2,3,4,5),
        --        (4,2,3,4,5),
        --        (5,2,3,4,5),
        --        (6,2,3,4,5),
        --        (7,2,3,4,5),
        --        (1,2,3,4,5);

        insert into coding_toast.user_test
        select * from (
                          values
                              (1, 'sam park', '010-0000-0001', 2,4) ,
                              (2, 'don joe','010-0002-0003', 2,4) ,
                              (3, 'tom millar','010-0004-0005', 2,4) ,
                              (4, 'Good mand','010-0006-0007', 2,4)
                      ) as tt;


    END;
$$;
--END
;

select * from coding_toast."user";
select * from coding_toast."user_test";


insert into coding_toast."user" (id, name, phone_number)
values (123, 5,1);


with user_temp(id, name, phone_number) as (
    values
        (1, 'sam park', '010-0000-0001') ,
        (2, 'don joe','010-0002-0003') ,
        (3, 'tom millar','010-0004-0005') ,
        (4, 'Good mand','010-0006-0007')
)
select * from user_temp
;

insert into coding_toast.user(name, phone_number)
select name, phone_number from (
                                   select name, phone_number from (values
                                                                       (1, '010-0000-0001') ,
                                                                       (2,'010-0002-0003') ,
                                                                       (3,'010-0004-0005') ,
                                                                       (4,'010-0006-0007')) as t(id, phone_number)
                                                                      join (values
                                                                                (1, 'sam park') ,
                                                                                (2, 'don joe') ,
                                                                                (3, 'tom millar') ,
                                                                                (4, 'Good mand')
                                   )  as tb(id, name) on t.id = tb.id
                               ) as insert_target_dataset
;

select t.name, t.phone_number, address.zipcode
from (values
          ('sam park', '010-0000-0001') ,
          ('don joe','010-0002-0003') ,
          ('tom millar','010-0004-0005') ,
          ('Good mand','010-0006-0007')) as t(name, phone_number)
         join (values
                   ('sam park', '000123') ,
                   ('don joe','000125') ,
                   ('tom millar','020123') ,
                   ('Good mand','005123')
) as address(name, zipcode) on t.name = address.name;



DO
$$
    BEGIN
        create table coding_toast.user_test
        (
            id    character varying(20),
            attr1 character varying(20),
            attr2 character varying(20),
            attr3 character varying(20),
            attr4 character varying(20),
            geom  geometry(multipolygon, 5186)
        );
        insert into coding_toast.user_test (id, attr1, attr2, attr3, attr4, geom)
        values (1, 2, 3, 4, 5, null),
               (2, 2, 3, 4, 5, null);
    END
$$
;
insert into coding_toast.user(name, phone_number)
select name, phone_number from (
                                   select name, phone_number from (values
                                                                       (1, '010-0000-0001') ,
                                                                       (2,'010-0002-0003') ,
                                                                       (3,'010-0004-0005') ,
                                                                       (4,'010-0006-0007')) as t(id, phone_number)
                                                                      join (values
                                                                                (1, 'sam park') ,
                                                                                (2, 'don joe') ,
                                                                                (3, 'tom millar') ,
                                                                                (4, 'Good mand')
                                   )  as tb(id, name) on t.id = tb.id
                               ) as insert_target_dataset;

insert into coding_toast.user_test (id, attr1, attr2, attr3, attr4, geom)
select * from (
                  select *
                  from (values(
                                  ('New_York',1,2,3,4),
                                  ('California',2,3,4,5),
                                  ('Texas',123,123,123,123),
                                  ('Arizona',99,23,12,51)
                              )) as ttt(id, attr1, attr2, attr3, attr4)

              ) as insert_target_dataset;

-- update coding_toast.address
-- set geom = (st_geomfromewkt('SRID=5186;Point(244196.625072 485941.889804)'));

insert into coding_toast.user_test (id, attr1, attr2, attr3, attr4, geom)
select t.*, st_asewkt(ttt."geom") from
    (
        values
            ('New_York',1,2,3,4),
            ('California',2,3,4,5),
            ('Texas',123,123,123,123),
            ('Arizona',99,23,12,51)
    ) as t("id", "attr1", "attr2", "attr3", "attr4")
        join coding_toast.address ttt
             on t.id = ttt.city_name
;




select name, phone_number from
    (
        values
            (1, '010-0000-0001') ,
            (2,'010-0002-0003') ,
            (3,'010-0004-0005') ,
            (4,'010-0006-0007')
    ) as t(id, phone_number)
        join (values
                  (1, 'sam park') ,
                  (2, 'don joe') ,
                  (3, 'tom millar') ,
                  (4, 'Good mand')
    )  as tb(id, name) on t.id = tb.id

