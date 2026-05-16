package exceptions;

public class UnauthorizedAccessException extends Exception {
    
    public UnauthorizedAccessException(String username, String action) {
        super("Access denied: '" + username + "' cannot perform: " + action);
    }
    
}
