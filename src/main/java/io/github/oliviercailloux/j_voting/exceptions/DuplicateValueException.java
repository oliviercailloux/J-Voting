package io.github.oliviercailloux.j_voting.exceptions;

public class DuplicateValueException extends Exception {

    public DuplicateValueException() {
        super();
    }

    public DuplicateValueException(String message) {
        super(message);
    }
}
