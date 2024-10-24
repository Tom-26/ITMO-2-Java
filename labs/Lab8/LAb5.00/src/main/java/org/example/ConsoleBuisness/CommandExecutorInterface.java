package org.example.ConsoleBuisness;

import org.example.models.User;

public interface CommandExecutorInterface {
    void setCurrentUser(User user);
    boolean isUserLoggedIn();
    User getCurrentUser();
}
