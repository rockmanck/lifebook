package pp.ua.lifebook.web.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;
import pp.ua.lifebook.user.parameters.DefaultTab;
import pp.ua.lifebook.user.parameters.UserSettings;

@Service
public class LbUserService implements UserService {

    private final UsersStorage repository;
    private final PasswordEncoder passwordEncoder;

    public LbUserService(UsersStorage repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerNewUser(UserDto userDto) {
        final var user = new User();
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());

        final String encoded = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encoded);

        final UserSettings userSettings = new UserSettings();
        userSettings.setDefaultTab(DefaultTab.WEEKLY);
        user.setUserSettings(userSettings);

        repository.addUser(user);
    }
}
