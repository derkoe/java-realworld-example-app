package realworld.base.users;

import javax.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdate {

  @Email
  String email;

  String password;
  String username;
  String bio;
  String image;
}
