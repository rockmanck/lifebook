package ua.lifebook.users;

import ua.lifebook.db.repository.Serial;
import ua.lifebook.db.repository.Table;

import java.util.HashSet;
import java.util.Set;

@Table("Users")
public class User {
    @Serial private Integer id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean admin;
    private Language language;
    private Set<ViewOption> viewOptions = new HashSet<>();

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

    public void addViewOption(ViewOption option) {
        viewOptions.add(option);
    }

    public Set<ViewOption> getViewOptions() {
        return viewOptions;
    }
}
