# Flowchart Application

This is a Spring Boot application for managing flowcharts, including creating, updating, deleting, and interacting with nodes and edges in a flowchart. It uses a relational database (MySQL or H2) for persistence and provides an API to interact with the flowchart data.

---

## **Table of Contents**

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
  - [Running Locally](#running-locally)
  - [Running with Docker](#running-with-docker)
  - [Running Tests](#running-tests)
- [API Documentation](#api-documentation)
- [GitHub Actions CI/CD](#github-actions-cicd)
- [Contributing](#contributing)
- [License](#license)

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

git clone https://github.com/debashis-dev/Flowchart-Management.git<br>
cd flowchart-app<br>

## **Set Up the Database**

If you're using MySQL, make sure the database exists, and you have the correct credentials set up in src/main/resources/application.properties:<br>

