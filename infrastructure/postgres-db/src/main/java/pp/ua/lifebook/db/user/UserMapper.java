package pp.ua.lifebook.db.user;

import pp.ua.lifebook.storage.db.scheme.tables.records.UsersRecord;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.parameters.Language;

class UserMapper {
    static User from(UsersRecord record) {
        return User.builder()
            .setId(record.getId())
            .setLogin(record.getLogin())
            .setPassword(record.getPassword())
            .setEmail(record.getEmail())
            .setAdmin(record.getIsAdmin() != null ? record.getIsAdmin() : false)
            .setFirstName(record.getFirstName())
            .setLastName(record.getLastName())
            .setLanguage(Language.byCode(record.getLanguage()))
            .createUser();
    }
}
