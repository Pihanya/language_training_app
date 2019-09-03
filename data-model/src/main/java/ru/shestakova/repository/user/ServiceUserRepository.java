package ru.shestakova.repository.user;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import ru.shestakova.model.user.ServiceUser;

@Repository
public interface ServiceUserRepository extends JpaRepository<ServiceUser, Long> {

  @Async
  CompletableFuture<ServiceUser> findByUserId(Long userId);

  @Async
  CompletableFuture<Optional<ServiceUser>> findByUsername(String userName);

  @Async
  CompletableFuture<Optional<ServiceUser>> findByEmail(String email);

  @Async
  CompletableFuture<Optional<ServiceUser>> findByVkId(Long vkId);
}
