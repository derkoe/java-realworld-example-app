package realworld.base.users;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserData {

  String username;
  String email;
  String bio;
  String image;
  String token;
}
