package exceptions;

public class InvalidLoginException extends Exception {
    
    public InvalidLoginException(String username) {
        super("Login failed for: " + username + ". Wrong username or password.");
    }

}