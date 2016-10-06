# Spring Sandbox

Project aimed at testing various frameworks, all around Spring.

## Configuration
To build and run the project, you have to:
* configure a postgre instance 
* configure the application through the application.properties file

If you have docker running, use the following command to create a postgres instance:
```
docker run -d --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres
```

Below is an example of the configuration file using postgres:
```
jdbc.driver=org.postgresql.Driver
jdbc.url=jdbc:postgresql://192.168.99.100:5432/postgres
jdbc.username=postgres
jdbc.password=postgres

hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

spring.security.rememberMe.key=Just_t1p3_a_s3CuR3_0f_Ur_cho1s3
```

## Run
After that you can use the following commands:
```
mvn package
mvn jetty:run
```

Open your browser at http://localhost:8080/ to see the application.

