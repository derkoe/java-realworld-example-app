package realworld.base.users.web;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import realworld.base.users.UserRegistration;
import realworld.base.users.UserService;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersResource {

  final UserService userService;

  @PostMapping
  public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest<UserRegistration> request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(userService.createUser(request.user)));
  }

  @PostMapping("/login")
  public UserResponse login(@Valid @RequestBody UserRequest<UserLogin> request) {
    return new UserResponse(userService.login(request.user));
  }
}
