package realworld.base.articles.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import realworld.base.articles.ArticleData;

@AllArgsConstructor
@Getter
class ArticleResponse {

  ArticleData article;
}
