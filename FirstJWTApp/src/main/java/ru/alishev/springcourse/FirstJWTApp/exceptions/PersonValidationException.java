package ru.alishev.springcourse.FirstJWTApp.exceptions;

public class PersonValidationException extends RuntimeException {
    public PersonValidationException() {
        super();
    }

    public PersonValidationException(String message) {
        super(message);
    }
}
