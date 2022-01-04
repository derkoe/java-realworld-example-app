package realworld.base.users.web;

import javax.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import realworld.base.security.AuthenticationService;
import realworld.base.users.UserService;
import realworld.base.users.UserUpdate;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserResource {

  final UserService userService;

  final AuthenticationService authenticationService;

  @GetMapping
  @RolesAllowed("User")
  public UserResponse currentUser() {
    return userService.getUser(authenticationService.getCurrentUserId()).map(UserResponse::new).orElse(null);
  }

  @PutMapping
  @RolesAllowed("User")
  public UserResponse updateUser(@RequestBody UserRequest<UserUpdate> request) {
    return new UserResponse(userService.updateUser(authenticationService.getCurrentUserId(), request.user));
  }
}
