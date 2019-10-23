package ru.shestakova.api.model.filter;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.shestakova.api.model.text.WorkType;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class TextWorkFilter {

  Integer from = 0;
  Integer count = 50;

  Integer textId;
  Long authorId;

  List<WorkType> workTypes = Arrays.asList(WorkType.values());

  Integer ratingFrom = 0;
  Integer ratingTo = Integer.MAX_VALUE;

  Instant createdFrom = Instant.now().minus(Duration.ofDays(7));
  Instant createdTo = Instant.now();

  Instant editedFrom = Instant.now().minus(Duration.ofDays(7));
  Instant editedTo = Instant.now();
}
