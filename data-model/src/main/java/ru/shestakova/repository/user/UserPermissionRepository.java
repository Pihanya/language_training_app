package ru.shestakova.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shestakova.model.user.UserPermission;
import ru.shestakova.model.user.UserPermission.UserPermissionKey;

public interface UserPermissionRepository extends JpaRepository<UserPermission, UserPermissionKey> {
  
}
