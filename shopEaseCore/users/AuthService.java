package users;

import java.util.ArrayList;

import exceptions.InvalidLoginException;
import exceptions.UnauthorizedAccessException;

public class AuthService {
    
    private ArrayList <User> users;
    private User currentUser;

    public AuthService() {
        users = new ArrayList<User>();
        currentUser = null;
    }

    public void registerUser (User user) {
        users.add(user);
        System.out.println("[Auth] Registered: " + user.getUsername() + " as " + user.getRole());
    }

    public User login (String username, String password) throws InvalidLoginException {
        for (int i=0 ; i < users.size() ; i++) {
            User u = users.get(i);
            if (u.getUsername().equals(username) && u.checkPassword(password)) {
                currentUser = u;
                System.out.println("[Auth] Login OK: " + username + " | Role: " + u.getRole() );
                return u;
            }
        }
        throw new InvalidLoginException (username);
    }
    
    public void logOut() {
        if (currentUser != null) {
            System.out.println("[Auth] Logged out:" + currentUser.getUsername());
            currentUser = null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void requireAdmin (String action) throws UnauthorizedAccessException {
        if (currentUser == null) {
            throw new UnauthorizedAccessException("Guest", action);
        }
        if (!currentUser.getRole().equals("ADMIN")) {
            throw new UnauthorizedAccessException(currentUser.getUsername(), action);
        }
    }

    public void requireCustomer(String action) throws UnauthorizedAccessException {
        if (currentUser == null) {
            throw new UnauthorizedAccessException("GUEST", action);
        }
        if (!currentUser.getRole().equals("CUSTOMER")) {
            throw new UnauthorizedAccessException(currentUser.getUsername(), action);
        }
    }
}
