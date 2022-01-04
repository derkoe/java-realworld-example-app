package realworld.base.articles;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tags")
@NoArgsConstructor
@Getter
@Setter
public class Tag {

  @Id
  @GeneratedValue
  private UUID id;

  @NotBlank
  private String name;

  Tag(String name) {
    this.name = name;
  }
}
