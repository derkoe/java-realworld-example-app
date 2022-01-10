package realworld.base.articles.web;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import realworld.base.articles.ArticleData;
import realworld.base.articles.ArticleService;
import realworld.base.articles.CommentData;
import realworld.base.articles.CreateArticle;
import realworld.base.security.AuthenticationService;

@RestController
@RequestMapping("/api/articles")
@AllArgsConstructor
public class ArticlesResource {

  final ArticleService articleService;

  final AuthenticationService authenticationService;

  @GetMapping
  public ArticlesResponse all(
    @RequestParam(name = "author", required = false) String author,
    @RequestParam(name = "tag", required = false) String tag
  ) {
    List<ArticleData> articles = articleService.search(author, tag);
    return new ArticlesResponse(articles, articles.size());
  }

  @GetMapping("/{slug}")
  public ArticleResponse articleBySlug(@PathVariable("slug") String slug) {
    return new ArticleResponse(articleService.findBySlug(slug, authenticationService.getCurrentUserId()));
  }

  @DeleteMapping("/{slug}")
  @RolesAllowed("User")
  public ArticleResponse delete(@PathVariable("slug") String slug) {
    return new ArticleResponse(articleService.deleteBySlug(slug, authenticationService.getCurrentUserId()));
  }

  @GetMapping("/feed")
  public ArticlesResponse feed(
    @RequestParam(name = "offset", defaultValue = "0") Integer offset,
    @RequestParam(name = "limit", defaultValue = "20") Integer limit
  ) {
    List<ArticleData> articles = articleService.feed(offset, limit);
    return new ArticlesResponse(articles, articles.size());
  }

  @PostMapping
  @RolesAllowed("User")
  public ArticleResponse create(@RequestBody ArticleRequest<CreateArticle> createArticle) {
    return new ArticleResponse(articleService.create(createArticle.article, authenticationService.getCurrentUserId()));
  }

  @PutMapping("/{slug}")
  @RolesAllowed("User")
  public ArticleResponse update(
    @PathVariable("slug") String slug,
    @RequestBody ArticleRequest<CreateArticle> createArticle
  ) {
    return new ArticleResponse(
      articleService.update(slug, createArticle.article, authenticationService.getCurrentUserId())
    );
  }

  @PostMapping("/{slug}/favorite")
  @RolesAllowed("User")
  public ArticleResponse favor(@PathVariable("slug") String slug) {
    return new ArticleResponse(articleService.addFavorite(slug, authenticationService.getCurrentUserId()));
  }

  @DeleteMapping("/{slug}/favorite")
  @RolesAllowed("User")
  public ArticleResponse unfavor(@PathVariable("slug") String slug) {
    return new ArticleResponse(articleService.removeFavorite(slug, authenticationService.getCurrentUserId()));
  }

  @PostMapping("/{slug}/comments")
  @RolesAllowed("User")
  public Map<String, CommentData> comment(
    @PathVariable("slug") String slug,
    @RequestBody CommentRequest commentRequest
  ) {
    return Map.of(
      "comment",
      articleService.comment(slug, commentRequest.getComment(), authenticationService.getCurrentUserId())
    );
  }

  @GetMapping("/{slug}/comments")
  public CommentsResponse comments(@PathVariable("slug") String slug) {
    List<CommentData> comments = articleService.comments(slug);
    return new CommentsResponse(comments, comments.size());
  }

  @DeleteMapping("/{slug}/comments/{commentId}")
  @RolesAllowed("User")
  public Map<String, CommentData> comment(
    @PathVariable("slug") String slug,
    @PathVariable("commentId") UUID commentId,
    @RequestBody CommentRequest commentRequest
  ) {
    return Map.of("comment", articleService.deleteComment(commentId, authenticationService.getCurrentUserId()));
  }
}
