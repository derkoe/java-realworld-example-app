package realworld.quarkus;

import java.util.UUID;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Provider
@PreMatching
public class SecurityFilter implements ContainerRequestFilter {

  @Inject
  AuthenticationContext authCtx;

  @Context
  SecurityContext securityCtx;

  @Override
  public void filter(ContainerRequestContext requestContext) {
    if (securityCtx.getUserPrincipal() instanceof JsonWebToken userPrincipal) {
      authCtx.userId = UUID.fromString(userPrincipal.getSubject());
    }
  }
}
