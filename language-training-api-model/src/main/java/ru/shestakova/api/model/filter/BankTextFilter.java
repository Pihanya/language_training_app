package ru.shestakova.api.model.filter;

import java.time.Duration;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class BankTextFilter {

  Integer from = 0;
  Integer count = 50;

  Instant createdFrom = Instant.now().minus(Duration.ofDays(7));
  Instant createdTo = Instant.now();

  Instant editedFrom;
  Instant editedTo = Instant.now();
}
