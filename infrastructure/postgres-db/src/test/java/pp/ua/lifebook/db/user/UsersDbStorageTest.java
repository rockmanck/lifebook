package pp.ua.lifebook.db.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pp.ua.lifebook.db.BaseDbTestContainersTest;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsersDbStorageTest extends BaseDbTestContainersTest {

    @AfterEach
    void tearDown() {
        dslContext.execute("DELETE FROM users");
        dslContext.execute("DELETE FROM user_settings");
    }

    @Test
    void whenLoginNull_ThrowEmptyLogin() {
        assertThrows(UsersDbStorage.EmptyLogin.class, () -> usersManager().isAuthorized(null, null));
    }

    @Test
    void whenLoginEmpty_ThrowEmptyLogin() {
        assertThrows(UsersDbStorage.EmptyLogin.class, () -> usersManager().isAuthorized("", "test"));
    }

    @Test
    @DisplayName("When adding user with existing id Then throw UserDuplicate exception")
    void shouldThrowUserDuplicate() {
        // Given
        final User duplicateUser = User.builder()
                .setId(userId)
                .setLogin("test")
                .setPassword("")
                .createUser();

        // Then
        assertThrows(UsersDbStorage.UserDuplicate.class, () -> usersDbStorage.addUser(duplicateUser));
    }

    @Test
    void whenUserAdded_CanAuthorize() {
        final User user = User.builder()
                .setLogin("test")
                .setPassword("")
                .setUserSettings(getUserSettings())
                .createUser();
        usersDbStorage.addUser(user);
        assertTrue(usersDbStorage.isAuthorized("test", ""));
    }

    @Test
    void whenUnknownUser_ThrowNoSuchUser() {
        assertThrows(UsersDbStorage.NoSuchUser.class, () -> usersManager().getUser("test", ""));
    }

    @Test
    @DisplayName("When add user Then user persisted in a database")
    void shouldStoreUserInDb() {
        // Given
        final var user = User.builder()
                .setLogin("test")
                .setPassword("")
                .setUserSettings(getUserSettings())
                .createUser();

        // When
        usersDbStorage.addUser(user);
        final User foundUser = usersDbStorage.findByLogin("test");

        // Then
        var expecteUserId = 2;
        assertThat(foundUser)
                .isNotNull()
                .extracting(User::getLogin, User::getPassword, User::getId)
                .containsExactly("test", "", expecteUserId);
    }

    private UsersStorage usersManager() {
        return new UsersStorageMock();
    }
}