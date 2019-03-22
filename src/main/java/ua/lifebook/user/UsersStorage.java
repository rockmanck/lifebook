package ua.lifebook.user;

public interface UsersStorage {
    void updateSettings(String options, String defaultTab, User user);

    boolean isAuthorized(String login, String password);

    User getUser(String login, String password);

    void addUser(User user);
}
