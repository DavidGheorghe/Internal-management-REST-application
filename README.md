# Backend part of Internal Management Order Application.

## Description
The project is a REST API, implemented in Spring Boot, connected to a MySQL database, and is used to handle the authentication and authorization,
it represents the business logic of the project and with Hibernate and JPA it updates the database entities. The authentication and authorization 
is implemented using JWT and Spring Security, and the tokens are stored in Cookies. The overall architecture is a layered, common for Spring Boot apps,
using Model-Service-Controller layers, and for the communication with the client side is used DTO pattern. 

The application is built based on specific requirements. From these requirements the design is created and implemented by the developer. 
The application is divided into three roles: Admin, Supervisor, and Employee, each role having its responsibilities. Moreover, there are several entites, such as Order, 
Customer, Product, Color, or Todo. On each entity is possible any CRUD operation, and has its blueprint in the database as well. 

A Dockerfile is available in the project, which builds the project, and makes it possible to use it further with other Docker containers.

## Instructions
- Clone the repository
- Creaate an `application.properties` file to connect the project with a database.
- Spring Web dependency is already available, but remove it from `pom.xml` when the application is ready for production.
