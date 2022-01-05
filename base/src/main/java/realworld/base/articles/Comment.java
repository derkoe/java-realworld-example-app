package realworld.base.articles;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
@Setter
public class Comment {

  @Id
  @GeneratedValue
  private UUID id;

  private String body;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "article_id")
  private UUID articleId;

  @CreationTimestamp
  @Column(name = "created_at")
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Instant updatedAt;

  public Comment(String body, UUID userId, UUID articleId) {
    this.body = body;
    this.userId = userId;
    this.articleId = articleId;
  }
}
