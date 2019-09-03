package ru.shestakova.repository.text;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import ru.shestakova.model.text.TextWork;

public interface TextWorkRepository extends JpaRepository<TextWork, Long> {

  default CompletableFuture<Optional<TextWork>> findBy(
      Integer textId, Integer authorId, Short typeId
  ) {
    return findByText_TextIdAndAuthor_UserIdAndType_TypeId(textId, authorId, typeId);
  }

  @Async
  CompletableFuture<Optional<TextWork>> findByText_TextIdAndAuthor_UserIdAndType_TypeId(
      Integer textId, Integer authorId, Short typeId
  );

}
