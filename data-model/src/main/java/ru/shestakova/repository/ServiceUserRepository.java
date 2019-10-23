package ru.shestakova.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shestakova.model.ServiceUser;

@Repository
public interface ServiceUserRepository extends JpaRepository<ServiceUser, Long> {

  Optional<ServiceUser> findByUsername(String userName);

  Optional<ServiceUser> findByEmail(String email);

  Optional<ServiceUser> findByAuthToken(UUID authToken);
}
