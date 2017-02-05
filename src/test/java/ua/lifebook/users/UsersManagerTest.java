package ua.lifebook.users;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ua.lifebook.db.UsersJdbc;

import static org.junit.Assert.assertTrue;

public class UsersManagerTest {
    @Mock private UsersJdbc usersJdbc;
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test(expected = UsersManager.EmptyLogin.class)
    public void WhenLoginNull_ThrowEmptyLogin() {
        usersManager().isAuthorized(null, null);
    }

    @Test(expected = UsersManager.EmptyLogin.class)
    public void WhenLoginEmpty_ThrowEmptyLogin() {
        usersManager().isAuthorized("", "test");
    }

    @Test
    public void WhenUserAdded_CanAuthorize() {
        final User user = new User();
        user.setLogin("test");
        user.setPassword("");
        final UsersManager usersManager = usersManager();
        usersManager.addUser(user);
        assertTrue(usersManager.isAuthorized("test", ""));
    }

    @Test(expected = UsersManager.NoSuchUser.class)
    public void WhenUnknownUser_ThrowNoSuchUser() {
        usersManager().getUser("test", "");
    }

    UsersManager usersManager() {
        return new UsersManager(usersJdbc);
    }
}