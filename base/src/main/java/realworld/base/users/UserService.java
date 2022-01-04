package realworld.base.users;

import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import realworld.base.security.AuthenticationService;
import realworld.base.users.web.UserLogin;
import realworld.base.web.ForbiddenException;
import realworld.base.web.NotFoundException;
import realworld.base.web.UnauthorizedException;

@Service
@AllArgsConstructor
public class UserService {

  final UserRepository userRepository;

  final AuthenticationService authenticationService;

  @Transactional
  public UserData createUser(UserRegistration registration) {
    if (userRepository.findByUsername(registration.username).isPresent()) {
      throw new IllegalArgumentException("Username already taken");
    }
    if (userRepository.findByEmail(registration.email).isPresent()) {
      throw new IllegalArgumentException("User with email already exists");
    }
    User user = new User();
    user.setUsername(registration.username);
    user.setPassword(authenticationService.encodePassword(registration.password));
    user.setEmail(registration.email);
    userRepository.save(user);
    return map(user);
  }

  @Transactional
  public UserData updateUser(UUID id, UserUpdate update) {
    User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
    if (update.getEmail() != null) user.setEmail(update.getEmail());
    if (update.getUsername() != null) user.setUsername(update.getUsername());
    if (update.getPassword() != null) user.setPassword(update.getPassword());
    if (update.getBio() != null) user.setBio(update.getBio());
    if (update.getImage() != null) user.setImage(update.getImage());
    return map(user);
  }

  public UserData login(UserLogin userLogin) {
    Optional<User> user = userRepository.findByEmail(userLogin.email);
    return user
      .map(u -> {
        if (authenticationService.verifyPassword(userLogin.password, u.getPassword())) {
          return map(u);
        }
        throw new UnauthorizedException();
      })
      .orElseThrow(UnauthorizedException::new);
  }

  public Optional<UserData> getUser(UUID id) {
    Optional<User> user = userRepository.findById(id);
    return user.map(this::map);
  }

  public Optional<ProfileData> getUserProfile(UUID userId, String username) {
    Optional<User> user = userRepository.findByUsername(username);
    return user.map(u -> mapProfile(u, userId));
  }

  @Transactional
  public ProfileData addFollower(UUID userId, String username) {
    User follower = userRepository.findById(userId).orElseThrow(ForbiddenException::new);
    Optional<User> user = userRepository.findByUsername(username);
    return user
      .map(u -> {
        u.getFollowers().add(follower);
        return mapProfile(user.get(), userId);
      })
      .orElse(null);
  }

  @Transactional
  public ProfileData removeFollower(UUID userId, String username) {
    User follower = userRepository.findById(userId).orElseThrow(ForbiddenException::new);
    Optional<User> user = userRepository.findByUsername(username);
    return user
      .map(u -> {
        u.getFollowers().remove(follower);
        return mapProfile(user.get(), userId);
      })
      .orElse(null);
  }

  public UserData map(User user) {
    String token = authenticationService.issueToken(user);

    return UserData
      .builder()
      .username(user.getUsername())
      .email(user.getEmail())
      .bio(user.getBio())
      .image(user.getImage())
      .token(token)
      .build();
  }

  public static ProfileData mapProfile(User user, UUID userId) {
    return ProfileData
      .builder()
      .username(user.getUsername())
      .bio(user.getBio())
      .image(user.getImage())
      .following(user.getFollowers().stream().anyMatch(u -> u.getId().equals(userId)))
      .build();
  }
}
