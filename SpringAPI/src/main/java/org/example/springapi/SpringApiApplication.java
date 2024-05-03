package org.example.springapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApiApplication {

    /*
    Create a new user:
         curl -X POST -H "Content-Type: application/json" -d '{"name":"User Name", "age":User Age, "email":"User Email"}' localhost:8080/users

    Update a user by ID:
        curl -X PUT -H "Content-Type: application/json" -d '{"name":"Benjamin", "age":21, "email":"benjamin@hocking.com"}' localhost:8080/users/{ID}

    Delete a user by ID:
        curl -X DELETE localhost:8080/users/{ID}
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringApiApplication.class, args);
    }

}
