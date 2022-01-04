package realworld.base.articles;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import realworld.base.users.UserRepository;
import realworld.base.web.ForbiddenException;
import realworld.base.web.NotFoundException;

@Service
@AllArgsConstructor
public class ArticleService {

  final ArticleRepository articleRepository;

  final TagRepository tagRepository;

  final UserRepository userRepository;

  final ArticleFavoriteRepository articleFavoriteRepository;

  final CommentRepository commentRepository;

  @Transactional
  public ArticleData create(CreateArticle createArticle, UUID userId) {
    Article article = new Article();
    article.setTitle(createArticle.getTitle());
    article.setSlug(slugify(createArticle.getTitle()));
    article.setAuthor(userRepository.findById(userId).orElseThrow(NotFoundException::new));
    article.setDescription(createArticle.getDescription());
    article.setBody(createArticle.getBody());
    article.setTags(
      createArticle
        .getTagList()
        .stream()
        .map(name ->
          tagRepository
            .findByName(name)
            .orElseGet(() -> {
              Tag tag = new Tag(name);
              tagRepository.save(tag);
              return tag;
            })
        )
        .collect(Collectors.toSet())
    );
    articleRepository.saveAndFlush(article);
    return map(article);
  }

  @Transactional
  public List<ArticleData> search(String author, String tag) {
    Stream<Article> articles;
    if (author != null) {
      articles = articleRepository.findByAuthorUserName(author);
    } else if (tag != null) {
      articles = articleRepository.findByTagName(tag);
    } else {
      articles = articleRepository.findAll().stream();
    }
    return articles.map(this::map).collect(Collectors.toList());
  }

  public List<ArticleData> feed(int offset, int limit) {
    return articleRepository
      .findAll(PageRequest.of(offset, limit))
      .stream()
      .map(this::map)
      .collect(Collectors.toList());
  }

  public ArticleData findBySlug(String slug, UUID userId) {
    Article article = articleRepository.findBySlug(slug);
    return mapWithFavorites(article, userId);
  }

  @Transactional
  public ArticleData update(String slug, CreateArticle changedArticle, UUID userId) {
    Article article = articleRepository.findBySlug(slug);
    if (!article.getAuthor().getId().equals(userId)) {
      throw new ForbiddenException();
    }
    if (changedArticle.getTitle() != null) {
      article.setTitle(changedArticle.getTitle());
    }
    if (changedArticle.getDescription() != null) {
      article.setDescription(changedArticle.getDescription());
    }
    if (changedArticle.getBody() != null) {
      article.setBody(changedArticle.getBody());
    }
    return map(article);
  }

  @Transactional
  public ArticleData addFavorite(String slug, UUID userId) {
    Article article = articleRepository.findBySlug(slug);
    articleFavoriteRepository.save(new ArticleFavorite(article, userId));
    return mapWithFavorites(articleRepository.findBySlug(slug), userId);
  }

  @Transactional
  public ArticleData removeFavorite(String slug, UUID userId) {
    Article article = articleRepository.findBySlug(slug);
    articleFavoriteRepository.deleteByArticleAndUserId(article, userId);
    return map(article);
  }

  @Transactional
  public ArticleData deleteBySlug(String slug, UUID userId) {
    Article article = articleRepository.findBySlug(slug);
    if (!article.getAuthor().getId().equals(userId)) {
      throw new ForbiddenException();
    }
    articleRepository.delete(article);
    return map(article);
  }

  private String slugify(String title) {
    return title.replaceAll("[^a-zA-Z0-9]", "-").toLowerCase();
  }

  private ArticleData map(Article article) {
    return mapWithFavorites(article, null);
  }

  private ArticleData mapWithFavorites(Article article, UUID userId) {
    if (article == null) {
      return null;
    }

    int favoriteCount = article.getFavorites().size();
    boolean favorited = false;
    if (userId != null) {
      favorited = article.getFavorites().stream().filter(af -> af.getUserId().equals(userId)).count() > 0;
    }

    List<String> tags = article.getTags().stream().map(Tag::getName).sorted().collect(Collectors.toList());
    return ArticleData
      .builder()
      .id(article.getId().toString())
      .slug(article.getSlug())
      .title(article.getTitle())
      .description(article.getDescription())
      .body(article.getBody())
      .favorited(favorited)
      .favoritesCount(favoriteCount)
      .createdAt(article.getCreatedAt())
      .updatedAt(article.getUpdatedAt())
      .tagList(tags)
      .author(article.getAuthor().getUsername())
      .build();
  }

  @Transactional
  public CommentData comment(String slug, CommentData comment, UUID userId) {
    Article article = articleRepository.findBySlug(slug);
    Comment c = new Comment(comment.getBody(), userId, article.getId());
    commentRepository.saveAndFlush(c);
    return new CommentData(c.getId(), c.getBody(), c.getCreatedAt(), c.getUpdatedAt(), c.getUserId().toString());
  }

  @Transactional
  public List<CommentData> comments(String slug) {
    Article article = articleRepository.findBySlug(slug);
    return commentRepository
      .findByArticleId(article.getId())
      .map(c -> new CommentData(c.getId(), c.getBody(), c.getCreatedAt(), c.getUpdatedAt(), c.getUserId().toString()))
      .collect(Collectors.toList());
  }
}
