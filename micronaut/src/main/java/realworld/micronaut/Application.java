package realworld.micronaut;

import io.micronaut.context.annotation.Import;
import io.micronaut.runtime.Micronaut;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Import(packages = "realworld.base")
@SpringBootApplication(scanBasePackages = { "realworld.base" })
@EnableJpaRepositories(basePackages = "realworld.base")
@EntityScan(basePackages = "realworld.base")
public class Application {

  public static void main(String[] args) {
    Micronaut.run(Application.class, args);
  }
}
