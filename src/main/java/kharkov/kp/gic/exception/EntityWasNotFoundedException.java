package kharkov.kp.gic.exception;

public class EntityWasNotFoundedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public EntityWasNotFoundedException() {
	}

	public EntityWasNotFoundedException(String message) {
		super(message);
	}

	public EntityWasNotFoundedException(String message, Throwable cause) {
		super(message, cause);
	}
}
