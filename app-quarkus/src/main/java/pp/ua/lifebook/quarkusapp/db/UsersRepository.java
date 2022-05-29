package pp.ua.lifebook.quarkusapp.db;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import pp.ua.lifebook.quarkusapp.db.user.UserEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsersRepository implements PanacheRepositoryBase<UserEntity, Integer> {
}
