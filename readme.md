# SpringAPI

## Project Description

SpringAPI is a RESTful API developed using Java and Spring Boot, designed to manage user information within a system. The API offers a complete set of CRUD (Create, Read, Update, Delete) operations that allow clients to manage user data efficiently. This project is structured around three main classes and one interface:

- **User**: Represents the user entity with attributes such as ID, name, age, and email.
- **UserController**: Acts as a REST controller, handling incoming HTTP requests and delegates operations to the UserService.
- **UserService**: Contains business logic to operate on user data and interacts with the UserRepository.
- **UserRepository**: An interface that extends `JpaRepository`, facilitating interaction with the database to store and retrieve user data.

The project exposes 5 endpoints:
- `GET /users`: Retrieve a list of all users.
- `GET /users/{id}`: Retrieve a specific user by ID.
- `POST /users`: Create a new user. The request body should include user details such as name, email, and age.
- `PUT /users/{id}`: Update an existing user by ID.
- `DELETE /users/{id}`: Delete a user by ID.

### Error Messages

This project returns helpful information to the user when a bad request has been made. For example, if the client requests a user ID that doesn't exist, the client is told that this resource could not be found.

### Field Validation

The `User` entity uses the `jakarta.validation.constraints` package to ensure that user fields are populated with valid data.

### Tech

The project leverages various tools, including:

- **Spring MVC** for routing and handling HTTP requests.
- **Spring Data JPA** for data access and manipulation through simple interface methods.
- **H2** for a simple in-memory database.
- **JUint** for unit tests
- **Mockito** for integration tests


## How to Build and Run the Project

### Building the Project

To build SpringAPI, follow these steps:

1. **Clone the repository:**

```
git clone https://github.com/benjaminhocking/SpringAPI.git
cd SpringAPI/SpringAPI
```

2. **Build the project using Maven:**

```
mvn clean package
```

This command compiles the project and runs any tests included in the suite, then packages the compiled code into a runnable jar file.

### Running the Project

After building the project, you can run it directly from the terminal:

```
java -jar target/SpringAPI-0.0.1-SNAPSHOT.jar
```

This command will start the Spring Boot application on the default port (8080), and you can access the API at `http://localhost:8080`.


## API Documentation

This project uses the swagger OpenAPI specification to create documentation.
This documentation can be viewed using a Swagger UI at `localhost:8080/swagger-ui/index.html`.

## Ideas for Potential Future Improvements

- **Security Enhancements**: Implement authentication and authorization mechanisms using Spring Security to protect the API and manage access controls based on user roles.
- **Advanced Search Capabilities**: Extend the UserRepository to support more complex queries, such as searching users by various criteria or implementing custom SQL queries with advanced filtering options.
