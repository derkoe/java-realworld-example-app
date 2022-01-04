package realworld.base.users.web;

import java.util.Map;
import javax.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import realworld.base.security.AuthenticationService;
import realworld.base.users.ProfileData;
import realworld.base.users.UserService;
import realworld.base.web.NotFoundException;

@RestController
@RequestMapping("/api/profiles")
@AllArgsConstructor
public class ProfilesResource {

  final UserService userService;

  final AuthenticationService authenticationService;

  @GetMapping("{username}")
  @RolesAllowed("User")
  public Map<String, ProfileData> userProfile(@PathVariable("username") String username) {
    return userService
      .getUserProfile(authenticationService.getCurrentUserId(), username)
      .map(u -> Map.of("profile", u))
      .orElseThrow(NotFoundException::new);
  }

  @PostMapping("{username}/follow")
  @RolesAllowed("User")
  public Map<String, ProfileData> follow(@PathVariable("username") String username) {
    return Map.of("profile", userService.addFollower(authenticationService.getCurrentUserId(), username));
  }

  @DeleteMapping("{username}/follow")
  @RolesAllowed("User")
  public Map<String, ProfileData> unfollow(@PathVariable("username") String username) {
    return Map.of("profile", userService.removeFollower(authenticationService.getCurrentUserId(), username));
  }
}
