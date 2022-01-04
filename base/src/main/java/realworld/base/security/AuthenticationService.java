package realworld.base.security;

import java.util.UUID;
import realworld.base.users.User;

public interface AuthenticationService {
  // TODO Jwt
  //      .issuer("https://quarkus-realworld.derkoe.dev")
  //      .subject(user.getId().toString())
  //      .upn(user.getEmail())
  //      .issuedAt(Instant.now())
  //      .expiresIn(Duration.ofMinutes(10))
  //      .groups(Set.of("User"))
  //      .sign();
  String issueToken(User user);

  UUID getCurrentUserId();

  String encodePassword(String password);

  boolean verifyPassword(String password, String hash);
}
