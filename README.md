# MyBatis Playground

<br>

## Configuration

### 1. create test scope application.yml file

> [test scope application.yml](src/test/resources/application.yml)

```yml
spring:
  h2:
    console:
      enabled: true
  datasource:
    driver-class-name: org.h2.Driver
    url:  jdbc:h2:mem:toast_bread;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH
    username: sa
    password: 1111
    ### do you want to use your own local h2 db? use the following configuration ###
    #url:  jdbc:h2:tcp://localhost/~/toast_bread;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE
  sql:
    init:
      mode: always
      platform: h2

mybatis:
  # mapper.xml location
  mapper-locations: classpath:mappers/**/*.xml
  # @Alias Class package location
  type-aliases-package: coding.toast.playground
  configuration:
    # db: snake_case => java: camelCase
    map-underscore-to-camel-case: true
    default-fetch-size: 10
    default-statement-timeout: 5
    jdbc-type-for-null: null

logging:
  level:
    root: info
    coding:
      toast:
        playground: debug
    org.springframework.jdbc.core.JdbcTemplate: debug
    org.springframework.jdbc.core.StatementCreatorUtils: trace
  pattern:
    console: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
```

<br><br>

### 2. create sql script for startup initialize

- [schema-h2.sql](src/test/resources/schema-h2.sql)

```postgresql
create schema IF NOT EXISTS coding_toast;

drop table if exists coding_toast.address;
create table if not exists coding_toast.address
(
    city_name varchar(50) not null,
    zipcode   varchar(50) not null
);

drop table if exists coding_toast."user";
create table if not exists  coding_toast."user"
(
    id           serial
        constraint user_pk
            primary key,
    name         varchar(50) not null,
    phone_number varchar(50)
);
```

<br>

- [data-h2.sql](src/test/resources/data-h2.sql)

```postgresql
insert into coding_toast.address (city_name, zipcode)
values  ('New York', '00000'),
        ('California', '11111'),
        ('Texas', '22222'),
        ('Arizona', '33333'),
        ('Indiana', '44444'),
        ('Washington', '55555'),
        ('Oklahoma', '66666');

insert into coding_toast."user" (id, name, phone_number)
values  (default, 'Charlie Puth', '010-0000-0001'),
        (default, 'Brad Pitt', '010-0002-0003'),
        (default, 'Daniel Radcliffe', '010-0004-0005'),
        (default, 'Freddie Highmore', '010-0006-0007'),
        (default, 'Gary Oldman', '010-0000-0001');
```



<br><br><br>

##  see also

- [how to initialize database on startup using script](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto.data-initialization.using-basic-sql-scripts)
- [mybatis configuration](https://mybatis.org/mybatis-3/configuration.html)
