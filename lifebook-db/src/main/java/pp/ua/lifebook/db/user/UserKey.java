package pp.ua.lifebook.db.user;

import pp.ua.lifebook.user.User;

final class UserKey {
    final String login;
    final String password;

    UserKey(String login, String password) {
        this.login = login;
        this.password = password;
    }

    static UserKey forUser(User user) {
        return new UserKey(user.getLogin(), user.getPassword());
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
