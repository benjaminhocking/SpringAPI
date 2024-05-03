package org.example.springapi;

public class UserNotFoundException extends RuntimeException {
    UserNotFoundException(Long id) {
        super("User not found with id " + id);
    }
}
