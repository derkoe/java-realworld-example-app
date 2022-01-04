package realworld.base.articles.web;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import realworld.base.articles.CommentData;

@AllArgsConstructor
@Getter
public class CommentsResponse {

  List<CommentData> comments;
  Integer commentsCount;
}
