# MyBatis Playground

## Configuration

- create 

<br><br>

## Coding History

### 1. create test scope application.yml file

> [test scope application.yml](src/test/resources/application.yml)

```yml
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres

mybatis:
  # mapper.xml location
  mapper-locations: classpath:mappers/**/*.xml
  configuration:
    # db: snake_case, java: camelCase
    map-underscore-to-camel-case: true
    default-fetch-size: 10
    default-statement-timeout: 5
  # @Alias
  type-aliases-package: coding.toast.playground
  
  
logging:
  level:
    root: info

```

<br><br>

##  see also

- [settings](https://mybatis.org/mybatis-3/configuration.html)