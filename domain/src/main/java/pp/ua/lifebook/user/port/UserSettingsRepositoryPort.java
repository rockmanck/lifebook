package pp.ua.lifebook.user.port;

import pp.ua.lifebook.user.parameters.UserSettings;

public interface UserSettingsRepositoryPort {
    UserSettings get(int userId);
}
