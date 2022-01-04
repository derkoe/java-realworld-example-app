package realworld.base.users;

import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue
  @Setter(AccessLevel.NONE)
  private UUID id;

  @NotEmpty
  private String username;

  @NotEmpty
  private String password;

  @Email
  @NotEmpty
  private String email;

  private String bio;
  private String image;

  @ManyToMany
  @JoinTable(
    name = "follows",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id")
  )
  private Set<User> followers;
}
