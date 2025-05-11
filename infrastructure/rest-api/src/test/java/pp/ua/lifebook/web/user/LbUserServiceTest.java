package pp.ua.lifebook.web.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pp.ua.lifebook.user.User;
import pp.ua.lifebook.user.UsersStorage;
import pp.ua.lifebook.user.parameters.DefaultTab;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LbUserServiceTest {

    @Mock
    private UsersStorage usersStorage;

    @Mock
    private PasswordEncoder passwordEncoder;

    private LbUserService lbUserService;

    @BeforeEach
    void setUp() {
        lbUserService = new LbUserService(usersStorage, passwordEncoder);
    }

    @Test
    @DisplayName("When registering a new user, the user should be added to the repository with encoded password")
    void registerNewUser_ShouldAddUserToRepository_WithEncodedPassword() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setLogin("testUser");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password123");
        
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn(encodedPassword);
        
        // When
        lbUserService.registerNewUser(userDto);
        
        // Then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(usersStorage).addUser(userCaptor.capture());
        
        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getLogin()).isEqualTo(userDto.getLogin());
        assertThat(capturedUser.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
        
        verify(passwordEncoder).encode(userDto.getPassword());
    }

    @Test
    @DisplayName("When registering a new user with null values, the user should still be added to the repository")
    void registerNewUser_WithNullValues_ShouldAddUserToRepository() {
        // Given
        UserDto userDto = new UserDto();
        // Login and email are null
        userDto.setPassword("password123");
        
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn(encodedPassword);
        
        // When
        lbUserService.registerNewUser(userDto);
        
        // Then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(usersStorage).addUser(userCaptor.capture());
        
        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getLogin()).isNull();
        assertThat(capturedUser.getEmail()).isNull();
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
        
        verify(passwordEncoder).encode(userDto.getPassword());
    }

    @Test
    @DisplayName("When registering a new user with empty values, the user should be added to the repository with empty values")
    void registerNewUser_WithEmptyValues_ShouldAddUserToRepository() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setLogin("");
        userDto.setEmail("");
        userDto.setPassword("");
        
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn(encodedPassword);
        
        // When
        lbUserService.registerNewUser(userDto);
        
        // Then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(usersStorage).addUser(userCaptor.capture());
        
        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getLogin()).isEmpty();
        assertThat(capturedUser.getEmail()).isEmpty();
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
        
        verify(passwordEncoder).encode(userDto.getPassword());
    }

    @Test
    @DisplayName("When default tab is missing Then use Weekly value")
    void shouldFallbackToWeeklyDefaultTab() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setLogin("testUser");
        userDto.setEmail("some@mail.com");
        userDto.setPassword("password123");

        // When
        lbUserService.registerNewUser(userDto);

        // Then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(usersStorage).addUser(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getUserSettings().getDefaultTab()).isEqualTo(DefaultTab.WEEKLY);
    }
}