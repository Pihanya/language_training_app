/*
package ru.shestakova.autoconfigure;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shestakova.model.ServiceUser;
import ru.shestakova.repository.ServiceUserRepository;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
@ContextConfiguration(classes = {ModelAutoConfiguration.class})
@DisplayName("Integration tests for ServiceUserRepository")
public class ServiceServiceUserRepositoryIntegrationTests {

  private static final Random RANDOM = new Random(666);

  private static final int GENERATED_USERS = 10;

  private static List<ServiceUser> users;

  @Autowired
  private ServiceUserRepository userRepository;

  @Before
  public void initTest() {
    users = Stream
        .generate(() -> {
              ServiceUser serviceUser = new ServiceUser()
                  .setUsername(String.valueOf(Math.abs(RANDOM.nextLong())))
                  .setEmail(Math.abs(RANDOM.nextLong()) + "@email.com")
                  .setVkId(Math.abs(RANDOM.nextLong()))
                  .setVkToken("token")
                  .setAvatarUrl("https://pornhub.com");

              UserDetails userDetails = new UserDetails()
                  .setRating(228)
                  .setScreenName("Sofya Shestakova")
                  .setUser(serviceUser);

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
  public void addAndFindById() {
    var expectedUser = userRepository.save(
        new ServiceUser()
            .setUsername(String.valueOf(Math.abs(RANDOM.nextLong())))
            .setEmail(Math.abs(RANDOM.nextLong()) + "@email.com")
            .setVkId(Math.abs(RANDOM.nextLong()))
            .setVkToken("token")
            .setAvatarUrl("https://pornhub.com")
    );

    var optional = userRepository.findById(expectedUser.getUserId());
    assertTrue(optional.isPresent());

    var actualUser = optional.get();
    assertEquals(expectedUser, actualUser);
  }

@Test
  @DisplayName("Add and find by userId")
  public void addAndFindByUserId() {
    var user = users.get(0);
    user = userRepository.save(user);

    var databaseUser = userRepository.findById(user.getUserId());

    assertNotNull(databaseUser, "Database should contain user");
    assertEquals(user, databaseUser, "User from database should equal saved user");
  }


@Test
  @DisplayName("Add and find by userName")
  public void addAndFindByUserName() {
    var user = users.get(0);
    user = userRepository.save(user);

    var databaseUserOptional = userRepository.findByUsername(user.getUsername());

    assertTrue(databaseUserOptional.isPresent(), "Database should contain user");
    assertEquals(user, databaseUserOptional.get(), "User from database should equal saved user");
  }


@Test
  @DisplayName("Add and find by email")
  public void addAndFindByEmail() {
    var user = users.get(0);
    user = userRepository.save(user);

    var databaseUserOptional = userRepository.findByEmail(user.getEmail());

    assertTrue(databaseUserOptional.isPresent(), "Database should contain user");
    assertEquals(user, databaseUserOptional.get(), "User from database should equal saved user");
  }

  @Test
  @DisplayName("Add and find by VKId")
  public void addAndFindByVKId() {
    var user = users.get(0);
    user = userRepository.save(user);

    var databaseUserOptional = userRepository.findByVkId(user.getVkId());

    assertTrue(databaseUserOptional.isPresent(), "Database should contain user");
    assertEquals(user, databaseUserOptional.get(), "User from database should equal saved user");
  }


}
*/
