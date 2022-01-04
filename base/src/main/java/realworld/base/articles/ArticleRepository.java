package realworld.base.articles;

import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
  @Query("SELECT a FROM Article a LEFT JOIN a.favorites f WHERE a.slug = :slug")
  Article findBySlug(@Param("slug") String slug);

  @Query("SELECT a FROM Article a LEFT JOIN a.favorites f LEFT JOIN a.tags tag WHERE tag.name = :name")
  Stream<Article> findByTagName(@Param("name") String name);

  @Query("SELECT a FROM Article a LEFT JOIN a.favorites f JOIN a.author author WHERE author.username = :username")
  Stream<Article> findByAuthorUserName(@Param("username") String username);
}
