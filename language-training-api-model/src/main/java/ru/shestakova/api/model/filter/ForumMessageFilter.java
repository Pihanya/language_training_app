package ru.shestakova.api.model.filter;

import java.time.Instant;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.shestakova.api.model.forum.MessageTerminationStatus;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class ForumMessageFilter {

  Integer themeId;
  Long authorId;

  Integer from = 0;
  Integer count = 50;

  Instant createdFrom = Instant.now().minus(Period.ofDays(7));
  Instant createdTo = Instant.now();

  Instant editedFrom;
  Instant editedTo = Instant.now();

  Instant deletedFrom;
  Instant deletedTo = Instant.now();

  List<MessageTerminationStatus> terminationStatuses =
      Collections.singletonList(MessageTerminationStatus.EXISTS);
}
