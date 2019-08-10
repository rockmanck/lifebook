package pp.ua.lifebook.db.user;

import org.junit.jupiter.api.Test;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsersStorageTest {

    @Test
    void whenLoginNull_ThrowEmptyLogin() {
        assertThrows(UsersDbStorage.EmptyLogin.class, () -> usersManager().isAuthorized(null, null));
    }

    @Test
    void whenLoginEmpty_ThrowEmptyLogin() {
        assertThrows(UsersDbStorage.EmptyLogin.class, () -> usersManager().isAuthorized("", "test"));
    }

    @Test
    void whenUserAdded_CanAuthorize() {
        final User user = User.builder()
            .setLogin("test")
            .setPassword("")
            .createUser();
        final UsersStorage usersStorage = usersManager();
        usersStorage.addUser(user);
        assertTrue(usersStorage.isAuthorized("test", ""));
    }

    @Test
    void whenUnknownUser_ThrowNoSuchUser() {
        assertThrows(UsersDbStorage.NoSuchUser.class, () -> usersManager().getUser("test", ""));
    }

    private UsersStorage usersManager() {
        return new UsersStorageMock();
    }
}