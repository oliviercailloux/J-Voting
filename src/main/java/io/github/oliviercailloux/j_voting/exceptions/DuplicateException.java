package io.github.oliviercailloux.j_voting.exceptions;

public class DuplicateException extends Exception {

    public DuplicateException() {
        super();
    }

    public DuplicateException(String message) {
        super(message);
    }
}
