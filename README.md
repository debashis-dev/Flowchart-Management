# Flowchart Application

This is a Spring Boot application for managing flowcharts, including creating, updating, deleting, and interacting with nodes and edges in a flowchart. It uses a relational database (MySQL or H2) for persistence and provides an API to interact with the flowchart data.

---

## **Table of Contents**

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
  - [Running Locally](#running-locally)
  - [Running Tests](#running-tests)
- [API Documentation](#api-documentation)

---

## **Features**

- **Create Flowchart**: Create a new flowchart with nodes and edges.
- **Update Flowchart**: Update existing flowcharts, add or remove nodes and edges.
- **Delete Flowchart**: Delete a flowchart along with its associated nodes and edges.
- **Get Flowchart**: Retrieve an existing flowchart by its ID.
- **Get Outgoing Edges**: Retrieve all outgoing edges for a specific node in a flowchart.
- **Get Connected Nodes**: Get a list of nodes connected to a given node.

---

## **Prerequisites**

Before running this project, ensure you have the following software installed on your local machine:

- **Java 11 or later**: Required to build and run the application.
- **Maven** or **Gradle**: For dependency management and building the project.
- **MySQL** (or H2 for testing): A database to persist data. You can use the embedded H2 database for testing purposes.

---

## **Installation**

### **Clone the Repository**

- git clone https://github.com/debashis-dev/Flowchart-Management.git
- cd flowchart-app

## **Set Up the Database**

If you're using MySQL, make sure the database exists, and you have the correct credentials set up in src/main/resources/application.properties:
- spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
- spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
- spring.datasource.username=your_username
- spring.datasource.password=your_password
- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.show-sql=true

If you're using H2, make sure to mention the below properties:
- spring.datasource.url=jdbc:h2:mem:testdb
- spring.datasource.driverClassName=org.h2.Driver
- spring.datasource.username=sa
- spring.datasource.password=password
- spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
Also if you want to change the port number, then mention it in application.properties
- server.port = 9123 (required port number)

## **Run Application**
- Run the Application as well as the test cases from the IDE

## **API Documentation**
- You can get the API documentation from the swagger api by running
- http://localhost:9123/swagger-ui.html

