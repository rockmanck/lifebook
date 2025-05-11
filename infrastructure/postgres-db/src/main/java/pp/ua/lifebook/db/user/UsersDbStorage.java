package pp.ua.lifebook.db.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import pp.ua.lifebook.storage.db.scheme.tables.records.UsersRecord;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;
import pp.ua.lifebook.user.parameters.DefaultTab;
import pp.ua.lifebook.user.parameters.UserSettings;
import pp.ua.lifebook.user.parameters.ViewOption;

import javax.sql.DataSource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static pp.ua.lifebook.storage.db.scheme.Tables.USERS;
import static pp.ua.lifebook.storage.db.scheme.Tables.USER_SETTINGS;

public class UsersDbStorage implements UsersStorage {
    private final UsersJdbc usersJdbc;
    private final DSLContext dslContext;
    private final Cache<UserKey, User> authorizationCache = CacheBuilder.newBuilder()
        .softValues()
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build();

    public UsersDbStorage(DataSource dataSource, DSLContext dslContext) {
        this.usersJdbc = new UsersJdbc(dataSource);
        this.dslContext = dslContext;
    }

    /**
     * Updates a view option in {@code user} and saves them into a database.
     * @param options {@link ViewOption} values joined by ','
     * @param defaultTab
     */
    @Override
    public void updateSettings(String options, String defaultTab, User user) {
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
    @Override
    public boolean isAuthorized(String login, String password) {
        if (isNotValidLogin(login)) throw new EmptyLogin();
        final User user = authorizationCache.getIfPresent(new UserKey(login, password));
        return user != null;
    }

    /**
     * Returns user by its login and password.
     * @throws NoSuchUser if login or password is incorrect
     * @throws EmptyLogin if login is empty
     */
    @Override
    public User getUser(String login, String password) {
        if (isNotValidLogin(login)) throw new EmptyLogin();

        final User user = getFromCacheOrLoadFromDb(login, password);

        if (user == null) throw new NoSuchUser();

        return user;
    }

    /**
     * Creates new user record in a database
     */
    @Override
    public void addUser(User user) {
        if (user.getId() != null) throw new UserDuplicate();

        final int userId = dslContext.insertInto(
                        USERS,
                        USERS.LOGIN,
                        USERS.PASSWORD,
                        USERS.FIRST_NAME,
                        USERS.LAST_NAME,
                        USERS.EMAIL
                )
                .values(user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail())
                .returningResult(USERS.ID)
                .fetchOne()
                .getValue(USERS.ID);

        final UserSettings userSettings = user.getUserSettings();
        dslContext.insertInto(
                        USER_SETTINGS,
                        USER_SETTINGS.USER_ID,
                        USER_SETTINGS.DEFAULT_TAB,
                        USER_SETTINGS.VIEW_OPTIONS
                ).values(
                        userId,
                        userSettings.getDefaultTab().name(),
                        getViewOptionsAsString(userSettings.getViewOptions())
                )
                .execute();

        putToCache(UserKey.forUser(user), user);
    }

    @Override
    public User findByLogin(String login) {
        UsersRecord user = dslContext.select()
            .from(USERS)
            .where(USERS.LOGIN.eq(login))
            .fetchOne()
            .into(UsersRecord.class);
        return user != null ? UserMapper.from(user) : null;
    }

    private boolean isNotValidLogin(String login) {
        return StringUtils.isEmpty(login);
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

    private static String getViewOptionsAsString(Set<ViewOption> options) {
        if (options == null || options.isEmpty()) {
            return "";
        }
        return options.toString();
    }

    public static class EmptyLogin extends RuntimeException {}
    public static class NoSuchUser extends RuntimeException {}
    public static class UserDuplicate extends RuntimeException {}
}
