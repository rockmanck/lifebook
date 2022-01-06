package pp.ua.lifebook.web.user;

import pp.ua.lifebook.storage.db.user.UserEntity;

public interface UserService {
    UserEntity registerNewUser(UserDto userDto);
}
