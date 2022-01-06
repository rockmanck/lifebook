package pp.ua.lifebook.web.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pp.ua.lifebook.storage.db.user.UserEntity;
import pp.ua.lifebook.storage.db.user.UserEntityRepository;
import pp.ua.lifebook.storage.db.user.UserSettingsRepository;
import pp.ua.lifebook.user.User;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;

    public DbUserDetailsService(UserEntityRepository userRepository, UserSettingsRepository userSettingsRepository) {
        this.userRepository = userRepository;
        this.userSettingsRepository = userSettingsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final UserEntity byLogin = userRepository.findByLogin(login);

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
