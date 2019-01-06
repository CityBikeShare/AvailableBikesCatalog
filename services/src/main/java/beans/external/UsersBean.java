package beans.external;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import external.Users;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsersBean {
    @Inject
    @DiscoverService(value = "userservice", version = "1.0.0", environment = "dev")
    private Optional<WebTarget> usersWebTarget;

    public List<Users> getUsers() {
        if (usersWebTarget.isPresent()) {
            WebTarget t = usersWebTarget.get();
            return t.path("sources/users").request().get(List.class);
        }
        return null;
    }

    public Users getUserById(int id) {
        if (usersWebTarget.isPresent()) {
            WebTarget t = usersWebTarget.get();
            return t.path("sources/users/" + id).request().get(Users.class);
        }
        return null;
    }

    public List<Users> getUsersByRegion(String region) {
        if (usersWebTarget.isPresent()) {
            WebTarget t = usersWebTarget.get();
            return t.path("sources/users/region/" + region).request().get(List.class);
        }
        return null;
    }
}