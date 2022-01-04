package realworld.base.articles.web;

import static java.util.stream.Collectors.toList;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import realworld.base.articles.Tag;
import realworld.base.articles.TagRepository;

@RestController
@RequestMapping("/api/tags")
@AllArgsConstructor
public class TagsResource {

  final TagRepository tagRepository;

  @GetMapping
  public TagsResponse tags() {
    return new TagsResponse(tagRepository.findAll().stream().map(Tag::getName).sorted().collect(toList()));
  }
}
