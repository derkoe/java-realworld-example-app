package realworld.quarkus;

import io.smallrye.jwt.build.Jwt;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import realworld.base.security.AuthenticationService;
import realworld.base.users.User;

@ApplicationScoped
public class QuarkusAuthenticationService implements AuthenticationService {

  @Inject
  AuthenticationContext authenticationContext;

  @Override
  public String issueToken(User user) {
    return Jwt
      .issuer("https://realworld.derkoe.dev")
      .subject(user.getId().toString())
      .upn(user.getEmail())
      .issuedAt(Instant.now())
      .expiresIn(Duration.ofMinutes(10))
      .groups(Set.of("User"))
      .sign();
  }

  @Override
  public UUID getCurrentUserId() {
    return authenticationContext.userId;
  }

  @Override
  public String encodePassword(String password) {
    return password; // TODO hash password
  }

  @Override
  public boolean verifyPassword(String password, String hash) {
    return password.equals(hash);
  }
}
