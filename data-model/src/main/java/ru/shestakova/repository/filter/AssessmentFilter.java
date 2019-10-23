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
public class AssessmentFilter {

  Integer from;
  Integer count;

  Long expertId;
  List<Integer> marks;

  Long createdFrom;
  Long createdTo;

  Long editedFrom;
  Long editedTo;
}
