package ru.shestakova.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shestakova.model.user.UserLevel;

public interface UserLevelRepository extends JpaRepository<UserLevel, Short> {

}
