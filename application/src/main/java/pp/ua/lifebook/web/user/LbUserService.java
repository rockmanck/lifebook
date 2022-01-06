package pp.ua.lifebook.web.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pp.ua.lifebook.storage.db.user.UserEntity;
import pp.ua.lifebook.storage.db.user.UserEntityRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class LbUserService implements UserService {

    private final UserEntityRepository repository;
    private final PasswordEncoder passwordEncoder;

    public LbUserService(UserEntityRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity registerNewUser(UserDto userDto) {
        final var user = new UserEntity();
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        final String encoded = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encoded);
        return repository.save(user);
    }
}
