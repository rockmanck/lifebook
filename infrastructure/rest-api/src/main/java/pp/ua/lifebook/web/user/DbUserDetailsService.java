package pp.ua.lifebook.web.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;
import pp.ua.lifebook.user.port.UserSettingsRepositoryPort;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UsersStorage userRepository;
    private final UserSettingsRepositoryPort userSettingsRepository;

    public DbUserDetailsService(UsersStorage userRepository, UserSettingsRepositoryPort userSettingsRepository) {
        this.userRepository = userRepository;
        this.userSettingsRepository = userSettingsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User byLogin = userRepository.findByLogin(login);

        if (byLogin == null) throw new UsernameNotFoundException(login);

        final var settings = userSettingsRepository.get(byLogin.getId());
        final var user = new User();
        user.setId(byLogin.getId());
        user.setLogin(byLogin.getLogin());
        user.setUserSettings(settings);
        user.setEmail(byLogin.getEmail());
        user.setFirstName(byLogin.getFirstName());
        user.setLastName(byLogin.getLastName());
        user.setPassword(byLogin.getPassword());
        return new LbUserPrincipal(user);
    }
}
