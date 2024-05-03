package org.example.springapi;

public class InvalidArgumentException extends RuntimeException {
    InvalidArgumentException() {
        super("Invalid argument");
    }
}
