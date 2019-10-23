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
import ru.shestakova.api.model.text.AssessmentMark;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class AssessmentFilter {

  Integer from = 0;
  Integer count = 50;

  Long expertId;
  List<AssessmentMark> marks = Arrays.asList(AssessmentMark.values());

  Instant createdFrom = Instant.now().minus(Duration.ofDays(7));
  Instant createdTo = Instant.now();

  Instant editedFrom;
  Instant editedTo = Instant.now();
}
