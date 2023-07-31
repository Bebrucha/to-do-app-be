Back-end for a to-do app using Java and Spring Boot with MySQL as the database. The project is set up with the following database configuration:

Database: MySQL
Table Name: tasks
id (primary key, non-nullable, auto-increment)
title (varchar(100))
description (varchar(200))
status (boolean)

To run the app, follow these steps:
Set up a MySQL using MySQL Workbench and create a database named "todo";
Update the database configuration in the application.properties file (usually found in src/main/resources) with your MySQL server credentials;
Run the app.

Current application.properties (Change based on your database setup)
spring.datasource.url=jdbc:mysql://localhost:3306/todo
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
