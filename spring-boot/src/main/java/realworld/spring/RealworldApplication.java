package realworld.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "realworld.base", "realworld.spring" })
@EnableJpaRepositories(basePackages = "realworld.base")
@EntityScan(basePackages = "realworld.base")
public class RealworldApplication {

  public static void main(String[] args) {
    SpringApplication.run(RealworldApplication.class, args);
  }
}
