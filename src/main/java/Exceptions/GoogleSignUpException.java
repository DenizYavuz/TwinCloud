package Exceptions;

public class GoogleSignUpException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public GoogleSignUpException(String name, String message) {
        super(message);
        this.name = name;
    }

	public String getName() {
		return name;
	}
	
	
}