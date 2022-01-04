package realworld.quarkus;

import java.util.UUID;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AuthenticationContext {

  UUID userId;
}
