package ru.shestakova.repository.filter;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class TextWorkFilter {

  Integer from;
  Integer count;

  Integer textId;
  Long authorId;

  List<Integer> workTypes;

  Integer ratingFrom = 0;
  Integer ratingTo = Integer.MAX_VALUE;

  Long createdFrom;
  Long createdTo;

  Long editedFrom;
  Long editedTo;
}
