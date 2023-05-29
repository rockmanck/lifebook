package pp.ua.lifebook.db.user;

import pp.ua.lifebook.storage.db.scheme.tables.records.UsersRecord;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.parameters.Language;

class UserMapper {
    private UserMapper() {
        throw new UnsupportedOperationException("It is not allowed to create instance of this class");
    }

    static User from(UsersRecord user) {
        return User.builder()
            .setId(user.getId())
            .setLogin(user.getLogin())
            .setPassword(user.getPassword())
            .setEmail(user.getEmail())
            .setAdmin(user.getIsAdmin() != null ? user.getIsAdmin() : false)
            .setFirstName(user.getFirstName())
            .setLastName(user.getLastName())
            .setLanguage(user.getLanguage() != null ? Language.byCode(user.getLanguage()) : Language.EN)
            .createUser();
    }
}
