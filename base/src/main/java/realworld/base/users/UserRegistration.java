package realworld.base.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRegistration {

  @NotBlank
  public String username;

  @NotBlank
  @Email
  public String email;

  @NotBlank
  public String password;
}
