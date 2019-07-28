package pp.ua.lifebook.db.user;

import org.junit.Test;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;

import static org.junit.Assert.assertTrue;

public class UsersStorageTest {

    @Test(expected = UsersDbStorage.EmptyLogin.class)
    public void WhenLoginNull_ThrowEmptyLogin() {
        usersManager().isAuthorized(null, null);
    }

    @Test(expected = UsersDbStorage.EmptyLogin.class)
    public void WhenLoginEmpty_ThrowEmptyLogin() {
        usersManager().isAuthorized("", "test");
    }

    @Test
    public void WhenUserAdded_CanAuthorize() {
        final User user = new User();
        user.setLogin("test");
        user.setPassword("");
        final UsersStorage usersStorage = usersManager();
        usersStorage.addUser(user);
        assertTrue(usersStorage.isAuthorized("test", ""));
    }

    @Test(expected = UsersDbStorage.NoSuchUser.class)
    public void WhenUnknownUser_ThrowNoSuchUser() {
        usersManager().getUser("test", "");
    }

    private UsersStorage usersManager() {
        return new UsersStorageMock();
    }
}