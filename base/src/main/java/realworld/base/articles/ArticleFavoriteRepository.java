package realworld.base.articles;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleFavoriteRepository extends JpaRepository<ArticleFavorite, UUID> {
  void deleteByArticleAndUserId(Article article, UUID userId);

  Optional<ArticleFavorite> findByArticleIdAndUserId(UUID articleId, UUID userId);

  long countByArticle(Article article);
}
