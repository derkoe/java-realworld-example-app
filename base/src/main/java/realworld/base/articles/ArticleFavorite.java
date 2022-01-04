package realworld.base.articles;

import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "articles_favorites")
@NoArgsConstructor
@Getter
@Setter
public class ArticleFavorite {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "article_id")
  private Article article;

  @Column(name = "user_id")
  private UUID userId;

  public ArticleFavorite(Article article, UUID userId) {
    this.article = article;
    this.userId = userId;
  }
}
