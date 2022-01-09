package realworld;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import realworld.base.security.AuthenticationService;
import realworld.base.users.User;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  final PasswordEncoder passwordEncoder;

  final JwtProperties jwtProperties;

  @Override
  public String issueToken(User user) {
    return JWT
      .create()
      .withIssuer("https://realworld.derkoe.dev")
      .withSubject(user.getId().toString())
      .withIssuedAt(new Date())
      .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 minutes
      .withArrayClaim("groups", new String[] { "User" })
      .withClaim("upn", user.getEmail())
      .sign(Algorithm.RSA256(jwtProperties.getPublicKey(), jwtProperties.getPrivateKey()));
  }

  @Override
  public UUID getCurrentUserId() {
    if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
      return UUID.fromString((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
    return null;
  }

  @Override
  public String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }

  @Override
  public boolean verifyPassword(String password, String hash) {
    return passwordEncoder.matches(password, hash);
  }
}
