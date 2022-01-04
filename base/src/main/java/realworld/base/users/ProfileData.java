package realworld.base.users;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileData {

  private String username;
  private String bio;
  private String image;
  private Boolean following;
}
