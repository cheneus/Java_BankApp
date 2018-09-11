package com.revature.exceptions;

public class NullArgumentException extends RuntimeException {

	private static final long serialVersionUID = -6008821094931414024L;

	public NullArgumentException(String message) {
		super(message);
	}

}
