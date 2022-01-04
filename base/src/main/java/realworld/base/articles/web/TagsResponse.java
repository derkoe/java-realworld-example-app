package realworld.base.articles.web;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TagsResponse {

  List<String> tags;
}
