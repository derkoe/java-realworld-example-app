package realworld.base.articles;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentData {

  UUID id;
  String body;
  Instant createdAt;
  Instant updatedAt;
  String author;
}
