package ru.shestakova.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shestakova.model.user.ServiceUser;
import ru.shestakova.model.user.UserDetails;
import ru.shestakova.model.user.UserLevel;
import ru.shestakova.repository.user.ServiceUserRepository;
import ru.shestakova.repository.user.UserLevelRepository;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
@ContextConfiguration(classes = {ModelAutoConfiguration.class})
@DisplayName("Integration tests for ServiceUserRepository")
public class ServiceServiceUserRepositoryIntegrationTests {

  private static final Random RANDOM = new Random(666);

  private static final int GENERATED_USERS = 10;

  private static UserLevel defaultLevel = new UserLevel()
      .withLevelNum((short) 0)
      .withName("Beginner")
      .withDescription("Beginner level");

  private static List<ServiceUser> users;

  @Autowired
  private ServiceUserRepository userRepository;

  @Autowired
  private UserLevelRepository userLevelRepository;

  @Before
  public void initTest() {
    defaultLevel = userLevelRepository.save(defaultLevel);

    users = Stream
        .generate(() -> {
              ServiceUser serviceUser = new ServiceUser()
                  .withUsername(String.valueOf(Math.abs(RANDOM.nextLong())))
                  .withEmail(Math.abs(RANDOM.nextLong()) + "@email.com")
                  .withVkId(Math.abs(RANDOM.nextLong()))
                  .withVkToken("token")
                  .withAvatarURL("https://pornhub.com");

              UserDetails userDetails = new UserDetails()
                  .withLevel(defaultLevel)
                  .withRating(228)
                  .withScreenName("Sofya Shestakova")
                  .withUser(serviceUser);

              serviceUser.setDetails(userDetails);

              return serviceUser;
            }
        )
        .limit(GENERATED_USERS)
        .collect(Collectors.toList());
  }

  @Before
  public void beforeTest() {
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("Add and find by userId")
  public void addAndFindByUserId() {
    var user = users.get(0);
    user = userRepository.save(user);

    var databaseUser = userRepository.findByUserId(user.getUserId()).join();

    assertNotNull(databaseUser, "Database should contain user");
    assertEquals(user, databaseUser, "User from database should equal saved user");
  }

  @Test
  @DisplayName("Add and find by userName")
  public void addAndFindByUserName() {
    var user = users.get(0);
    user = userRepository.save(user);

    var databaseUserOptional = userRepository.findByUsername(user.getUsername()).join();

    assertTrue(databaseUserOptional.isPresent(), "Database should contain user");
    assertEquals(user, databaseUserOptional.get(), "User from database should equal saved user");
  }

  @Test
  @DisplayName("Add and find by email")
  public void addAndFindByEmail() {
    var user = users.get(0);
    user = userRepository.save(user);

    var databaseUserOptional = userRepository.findByEmail(user.getEmail()).join();

    assertTrue(databaseUserOptional.isPresent(), "Database should contain user");
    assertEquals(user, databaseUserOptional.get(), "User from database should equal saved user");
  }

  @Test
  @DisplayName("Add and find by VKId")
  public void addAndFindByVKId() {
    var user = users.get(0);
    user = userRepository.save(user);

    var databaseUserOptional = userRepository.findByVkId(user.getVkId()).join();

    assertTrue(databaseUserOptional.isPresent(), "Database should contain user");
    assertEquals(user, databaseUserOptional.get(), "User from database should equal saved user");
  }

}
