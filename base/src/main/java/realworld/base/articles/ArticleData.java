package realworld.base.articles;

import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class ArticleData {

  String id;
  String slug;
  String title;
  String description;
  String body;
  boolean favorited;
  int favoritesCount;
  Instant createdAt;
  Instant updatedAt;
  List<String> tagList;
  String author;
}
