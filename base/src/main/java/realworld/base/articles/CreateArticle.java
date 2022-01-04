package realworld.base.articles;

import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CreateArticle {

  String title;
  String description;
  String body;
  Set<String> tagList;
}
