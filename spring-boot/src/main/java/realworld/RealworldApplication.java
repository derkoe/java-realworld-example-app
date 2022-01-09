package realworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.TypeHint;

@SpringBootApplication
@TypeHint(
  typeNames = { "org.hibernate.tuple.CreationTimestampGeneration", "org.hibernate.tuple.UpdateTimestampGeneration" }
)
public class RealworldApplication {

  public static void main(String[] args) {
    SpringApplication.run(RealworldApplication.class, args);
  }
}
