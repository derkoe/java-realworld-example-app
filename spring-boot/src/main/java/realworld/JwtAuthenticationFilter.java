package realworld;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

  public static final String AUTHENTICATION = "Authorization";
  public static final String TOKEN_PREFIX = "Token ";

  private final JwtProperties jwtProperties;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProperties jwtProperties) {
    super(authenticationManager);
    this.jwtProperties = jwtProperties;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    String header = request.getHeader(AUTHENTICATION);
    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }
    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(AUTHENTICATION);
    if (token != null) {
      // parse the token.
      DecodedJWT userToken = JWT
        .require(Algorithm.RSA256(jwtProperties.getPublicKey(), jwtProperties.getPrivateKey()))
        .build()
        .verify(token.replace(TOKEN_PREFIX, ""));
      if (userToken != null) {
        return new UsernamePasswordAuthenticationToken(
          userToken.getSubject(),
          token,
          userToken
            .getClaim("groups")
            .asList(String.class)
            .stream()
            .map(s -> "ROLE_" + s)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList())
        );
      }
      return null;
    }
    return null;
  }
}
