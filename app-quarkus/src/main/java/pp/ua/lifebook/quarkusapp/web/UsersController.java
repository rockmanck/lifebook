package pp.ua.lifebook.quarkusapp.web;

import pp.ua.lifebook.quarkusapp.db.UsersRepository;
import pp.ua.lifebook.quarkusapp.db.user.UserEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersController {

    @Inject
    UsersRepository repository;

    @Inject
    EntityManager entityManager;

    @GET
    @Path("/hibernate")
    public List<String> getAll() {
        return entityManager.createQuery("FROM users", UserEntity.class)
            .getResultList()
            .stream()
            .map(UserEntity::getLogin)
            .toList();
    }

    @GET
    @Path("/panache")
    public List<String> getAllPanache() {
        return repository.listAll()
            .stream()
            .map(UserEntity::getLogin)
            .toList();
    }

    @GET
    @Path("/{id}")
    public UserEntity loadUser(@PathParam("id") int id) {
        return repository.findById(id);
    }

}
