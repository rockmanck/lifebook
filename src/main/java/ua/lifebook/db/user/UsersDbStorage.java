package ua.lifebook.db.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import ua.lifebook.user.User;
import ua.lifebook.user.UsersStorage;
import ua.lifebook.user.parameters.DefaultTab;
import ua.lifebook.user.parameters.ViewOption;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

public class UsersDbStorage implements UsersStorage {
    private final UsersJdbc usersJdbc;
    private final Cache<UserKey, User> authorizationCache = CacheBuilder.newBuilder()
        .softValues()
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build();

    public UsersDbStorage(DataSource dataSource) {
        this.usersJdbc = new UsersJdbc(dataSource);
    }

    /**
     * Updates view option in {@code user} and saves them into database.
     * @param options {@link ViewOption} values joined by ','
     * @param defaultTab
     */
    @Override public void updateSettings(String options, String defaultTab, User user) {
        user.getUserSettings().setViewOptions(ViewOption.parse(options));
        user.getUserSettings().setDefaultTab(DefaultTab.valueOf(defaultTab));
        usersJdbc.updateUserSettings(options, user);
    }

    /**
     * Checks if such login and password authorized.
     * @param login not empty login
     * @param password for specified login
     * @throws EmptyLogin if {@code login == null || login.isEmpty()}
     * @return true if such login and password pair exists in User table
     */
    @Override public boolean isAuthorized(String login, String password) {
        if (!isValidLogin(login)) throw new EmptyLogin();
        final User user = authorizationCache.getIfPresent(new UserKey(login, password));
        return user != null;
    }

    /**
     * Returns user by its login and password.
     * @throws NoSuchUser if login or password is incorrect
     * @throws EmptyLogin if login is empty
     */
    @Override public User getUser(String login, String password) {
        if (!isValidLogin(login)) throw new EmptyLogin();

        final User user = getFromCacheOrLoadFromDb(login, password);

        if (user == null) throw new NoSuchUser();

        return user;
    }

    /**
     * Creates new user record in database
     * @param user
     */
    @Override public void addUser(User user) {
        // TODO add new user to db

        putToCache(UserKey.forUser(user), user);
    }

    private boolean isValidLogin(String login) {
        return !StringUtils.isEmpty(login);
    }

    private void putToCache(UserKey key, User value) {
        authorizationCache.put(key, value);
    }

    private User getFromCacheOrLoadFromDb(String login, String password) {
        final UserKey key = new UserKey(login, password);

        if (!isPresentInCache(key)) return loadFromDbToCache(key);

        return authorizationCache.getIfPresent(key);
    }

    private User loadFromDbToCache(UserKey key) {
        final User fromDb = usersJdbc.getUserByCreds(key.login, key.password);
        if (fromDb != null) {
            authorizationCache.put(key, fromDb);
        }
        return fromDb;
    }

    private boolean isPresentInCache(UserKey key) {
        return authorizationCache.getIfPresent(key) != null;
    }

    public static class EmptyLogin extends RuntimeException {}
    public static class NoSuchUser extends RuntimeException {}
    public static class UserDuplicate extends RuntimeException {}
}
