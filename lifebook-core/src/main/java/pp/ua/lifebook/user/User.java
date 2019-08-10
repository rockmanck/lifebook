package pp.ua.lifebook.user;

import pp.ua.lifebook.user.parameters.Language;
import pp.ua.lifebook.user.parameters.UserSettings;

public class User {
    private Integer id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean admin;
    private Language language;
    private UserSettings userSettings;

    private User(
        Integer id,
        String login,
        String firstName,
        String lastName,
        String email,
        String password,
        boolean admin,
        Language language,
        UserSettings userSettings
    ) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.language = language;
        this.userSettings = userSettings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static final class UserBuilder {

        private Integer id;
        private String login;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private boolean admin;
        private Language language;
        private UserSettings userSettings;

        private UserBuilder() {
            // hide public constructor
        }

        public UserBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setAdmin(boolean admin) {
            this.admin = admin;
            return this;
        }

        public UserBuilder setLanguage(Language language) {
            this.language = language;
            return this;
        }

        public UserBuilder setUserSettings(UserSettings userSettings) {
            this.userSettings = userSettings;
            return this;
        }

        public User createUser() {
            return new User(id, login, firstName, lastName, email, password, admin, language, userSettings);
        }
    }
}
