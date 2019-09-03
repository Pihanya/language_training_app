package ru.shestakova.repository.user;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import ru.shestakova.model.user.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Short> {

  @Async CompletableFuture<Optional<Permission>> findByName(String name);
}
