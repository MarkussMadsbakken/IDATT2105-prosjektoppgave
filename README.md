# IDATT2015 prosjektoppgave

## Contributors
- Markus Stuevold Madsbakken
- Nikolai Tandberg
- Jacob Lein
- Erik Hoff

## Table of contents
- [IDATT2015 prosjektoppgave](#idatt2015-prosjektoppgave)
  - [Contributors](#contributors)
  - [Table of contents](#table-of-contents)
  - [Project description](#project-description)
    - [Technology Stack](#technology-stack)
    - [Key Features](#key-features)
    - [Project Goals](#project-goals)
  - [Project structure](#project-structure)
    - [System architecture](#system-architecture)
    - [Entity-relationship diagram](#entity-relationship-diagram)
    - [System sequence diagram](#system-sequence-diagram)
    - [Limited class diagram](#limited-class-diagram)
    - [Frontend](#frontend-file-structure)
    - [Backend](#backend-file-structure)
  - [Installation](#installation)
  - [Usage](#usage)
  - [Testing](#testing)
  - [Further documentation](#further-documentation)
  - [Future work](#future-work)


## Project description
This project is an e-commerce marketplace web application similar to "finn.no", developed as part of the IDATT2105 Full-stack Application Development course. The platform allows sellers to list items for sale and buyers to browse, search, and purchase these items through various interactive features.
### Technology Stack
- Frontend:
  - npm (v18.16.0) for package management
  - Vue.js (v3) framework with custom CSS (no frameworks like Tailwind)
  - PrimeVue for UI components
  - Vue Query for data fetching and caching
  - Pinia for state management
  - i18n for internationalization/translation support
  - Cypress for end-to-end testing
- Backend:
  - Java v21 with Spring Boot/Spring Framework
  - Maven for dependency management
  - JUnit for unit testing
  - Mockito for mocking dependencies in tests
  - JDBC for database access
  - Swagger through OpenAPI for REST API documentation
  - JaCoCo for test coverage reporting
  - SLF4J for logging
- Database
  - MySQL (v8.1) for production
  - H2 for testing
  - Flyway for database migration
- Authentication: JWT and Spring Security
### Key Features
User authentication system with role-based access (normal users and administrators)
Item listing with detailed product information, images, and location data
Advanced search and filtering capabilities by categories, locations, and other parameters
Interactive item views (thumbnail and map views) with pagination support
Bookmarking system for users to save favorite items
In-app messaging between buyers and sellers
Price negotiation functionality
Mobile-responsive design
Internationalization support for multiple languages
Payment integration via VIPPS
Administrative tools for managing categories and users
### Project Goals
The aim of this project is to demonstrate full-stack development capabilities by creating a functional e-commerce marketplace with robust frontend and backend integration. The application follows industry best practices including OWASP security standards and universal design principles while maintaining comprehensive test coverage and documentation.

## Project structure
### System architecture
![system-architecture.png](docs/system-architecture.png)

### Entity-relationship diagram
![ER-diagram.png](docs/ER-diagram.png)

### System sequence diagram
![register-sequence-diagram.png](docs/register-sequence-diagram.png)

### limited class diagram
![limited-class-diagram.png](docs/limited-class-diagram.png)

### Frontend file structure
```plaintext
insert filestructure here
```

### Backend file structure
```plaintext
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── edu
│   │   │       └── ntnu
│   │   │           └── stud
│   │   │               ├── config
│   │   │               ├── controller
│   │   │               ├── exception
│   │   │               ├── factory
│   │   │               ├── filter
│   │   │               ├── model
│   │   │               │   ├── base
│   │   │               │   ├── request
│   │   │               │   ├── response
│   │   │               │   └── update
│   │   │               ├── repo
│   │   │               ├── service
│   │   │               └── util
│   │   └── resources
│   │       └── db
│   │           └── migration
│   └── test
│       ├── java
│       │   └── edu
│       │       └── ntnu
│       │           └── stud
│       │               ├── repo
│       │               ├── service
│       │               └── util
│       └── resources
│           └── db
│               └── testmigrations
└── target
```


## Installation
Run `npm install` in the `frontend` directory to install the frontend dependencies. 

Run `mvn clean install` in the `backend` directory to install the backend dependencies. This command will also run the tests and generate the necessary artifacts for deployment.

Alternatively, with make you can run `make install` in the root directory to install both the frontend and backend dependencies. This command will also run the tests and generate the necessary artifacts for deployment.

## Usage
Run `npm run dev` in the `frontend` directory to start the frontend development server. This will allow you to access the application at `http://localhost:5173`.

Run `mvn spring-boot:run` in the `backend` directory to start the backend server. This will allow you to access the API at `http://localhost:8080`.

Alternatively, with make you can run `make run` in the root directory to start both the frontend and backend servers. This will allow you to access the application at `http://localhost:5173` and the API at `http://localhost:8080`.

## Testing
Run `npm run test:unit` in the `frontend` directory to run the frontend tests. This will execute the unit tests and generate a coverage report.

Run `mvn test` in the `backend` directory to run the backend tests. This will execute the unit tests and generate a coverage report.

Alternatively, with make you can run `make test` in the root directory to run both the frontend and backend tests. This will execute the unit tests and generate a coverage report.

## Further documentation
Jacoco test coverage report can be found in the `target/site/jacoco/index.html` file after running the tests with `mvn test`. The report provides insights into the code coverage of the unit tests, helping to identify areas that may require additional testing. Jacoco may also veryfi the test coverage against the requierd coverage of 50%, by running the command `mvn verify`.

Rest API documentation is available at `http://localhost:8080/swagger-ui/index.html` when the application is running. This documentation provides an overview of the available endpoints, request/response formats, and authentication requirements. The documentation is generated using Swagger/OpenAPI and is automatically updated based on the annotations in the code.

The java code is documented using JavaDoc comments, which provide detailed descriptions of classes, methods, and parameters. The JavaDoc comments are intended to help developers understand the codebase and facilitate future maintenance and development.

The JS code is documented using JSDoc comments, which provide detailed descriptions of functions, parameters, and return values. The JSDoc comments are intended to help developers understand the codebase and facilitate future maintenance and development.

## Future work
