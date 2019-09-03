package ru.shestakova.repository.text;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import ru.shestakova.model.text.Assessment;

public interface AssessmentRepository
    extends JpaRepository<Assessment, Long> {


  default CompletableFuture<Optional<Assessment>> findBy(Long workId, Integer expertId) {
    return findByWork_WorkIdAndAndExpert_UserId(workId, expertId);
  }

  @Async CompletableFuture<Optional<Assessment>> findByWork_WorkIdAndAndExpert_UserId(
      Long workId, Integer expertId
  );
}
