package pp.ua.lifebook.storage.db.user;

import org.springframework.data.repository.CrudRepository;

public interface UserEntityRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findByLogin(String login);
}
