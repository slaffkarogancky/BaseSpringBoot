package kharkov.kp.gic.exception;

public class ResourceWasNotFoundedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ResourceWasNotFoundedException() {
	}

	public ResourceWasNotFoundedException(String message) {
		super(message);
	}

	public ResourceWasNotFoundedException(String message, Throwable cause) {
		super(message, cause);
	}
}
