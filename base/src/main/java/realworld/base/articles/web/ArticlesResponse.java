package realworld.base.articles.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import realworld.base.articles.ArticleData;

@AllArgsConstructor
@Getter
class ArticlesResponse {

  Iterable<ArticleData> articles;
  Integer articlesCount;
}
