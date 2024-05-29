# Introduction
Project utilize spring boot 3, MySQL and JWT

# Configuration
Configuration properties required 

````
application.name= spring-security

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/example
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


token.signing.key=<64 char string>
````
