# Student and Course Management System

## Overview

This project is a Spring Boot web application developed to manage information related to students and courses. The application demonstrates the implementation of Create, Read, and Update operations along with a custom inner join query to retrieve combined data from related entities.

The system follows a layered architecture approach and is designed using industry-standard practices to ensure maintainability, scalability, and clarity.

---

## Features

The application provides the following functionalities:

* Creation of student and course records
* Retrieval and display of student and course data
* Updating existing student records
* Execution of an inner join query between student and course entities
* Validation of user input using annotation-based constraints
* Centralized exception handling using a global exception handler
* Unit testing for service and repository layers

---

## Technology Stack

The application is built using the following technologies:

* Java 17
* Spring Boot
* Spring Data JPA with Hibernate
* JSP and JSTL for the view layer
* H2 or MySQL database
* Maven for build and dependency management
* JUnit 5 and Mockito for testing

---

## System Architecture

The project follows a layered architecture consisting of the following components:

* Controller Layer responsible for handling HTTP requests and responses
* Service Layer responsible for business logic
* Repository Layer responsible for database interaction
* Entity Layer representing database tables
* DTO Layer for structured data transfer
* Exception Handling Layer for centralized error management

---

## Entity Relationship

The system consists of two main entities:

* Student
* Course

A one-to-many relationship is established between Course and Student, where one course can have multiple students, and each student is associated with a single course.

---

## Setup Instructions

To run the application, follow these steps:

1. Clone the repository from GitHub
2. Open the project in an integrated development environment such as IntelliJ IDEA, Eclipse, or Visual Studio Code
3. Ensure that Java 17 and Maven are installed and properly configured
4. Update the database configuration in the application properties file if required
5. Build and run the project using Maven or the IDE

To run using Maven, execute the following command:

mvn spring-boot:run

---

## Application Access

Once the application is running, it can be accessed through the following URL:

[http://localhost:8080/](http://localhost:8080/)

---

## Testing

The project includes unit tests for both service and repository layers. These tests validate business logic and database interactions.

To execute the tests, run:

mvn test

---

## Challenges Faced

During the development of this project, several challenges were encountered:

* Designing an appropriate relationship between entities
* Implementing an efficient inner join query
* Handling validation and exception scenarios
* Integrating JSP with Spring Boot
* Configuring unit testing frameworks correctly

These challenges were addressed by adopting best practices such as using a one-to-many relationship, implementing a data transfer object for query results, and applying proper validation and exception handling techniques.

---

## Conclusion

This project successfully demonstrates the development of a Spring Boot application with a structured architecture, proper database design, and implementation of core CRUD operations. It also highlights the use of modern development practices including validation, exception handling, and testing.

---

## License

This project is intended for academic purposes only.
