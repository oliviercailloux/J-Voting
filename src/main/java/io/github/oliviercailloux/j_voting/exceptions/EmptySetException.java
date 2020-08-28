package io.github.oliviercailloux.j_voting.exceptions;

public class EmptySetException extends Exception {

	public EmptySetException() {
		super();
	}

	public EmptySetException(String messageString) {
		super(messageString);
	}
}
