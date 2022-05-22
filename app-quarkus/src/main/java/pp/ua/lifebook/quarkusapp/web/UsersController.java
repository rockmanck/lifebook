package pp.ua.lifebook.quarkusapp.web;

import pp.ua.lifebook.quarkusapp.db.UsersRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UsersController {

    @Inject
    UsersRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAll() {
        return repository.getAll();
    }
}
