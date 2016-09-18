package ua.lifebook.web;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ua.lifebook.db.UsersJdbc;
import ua.lifebook.users.User;

import java.util.concurrent.TimeUnit;

public class Authorization {
    private final UsersJdbc jdbc;
    private final LoadingCache<UserKey, User> loginCache = CacheBuilder.newBuilder()
        .softValues()
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build(new CacheLoader<UserKey, User>() {
            @Override public User load(UserKey key) throws Exception {
                return jdbc.loginUser(key.login, key.password);
            }
        });

    @Autowired
    public Authorization(UsersJdbc jdbc) {
        this.jdbc = jdbc;
    }

    public User login(String login, String password) {
        if (StringUtils.isEmpty(login) && StringUtils.isEmpty(password)) return null;
        return loginCache.getUnchecked(new UserKey(login, password));
    }

    private final class UserKey {
        private final String login;
        private final String password;

        private UserKey(String login, String password) {
            this.login = login;
            this.password = password;
        }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UserKey userKey = (UserKey) o;

            if (!login.equals(userKey.login)) return false;
            return password.equals(userKey.password);

        }

        @Override public int hashCode() {
            int result = login.hashCode();
            result = 31 * result + password.hashCode();
            return result;
        }
    }
}
