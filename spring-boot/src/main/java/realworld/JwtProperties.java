package realworld;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("realworld.jwt")
@Getter
@Setter
public class JwtProperties {

  private RSAPrivateKey privateKey;
  private RSAPublicKey publicKey;
}
