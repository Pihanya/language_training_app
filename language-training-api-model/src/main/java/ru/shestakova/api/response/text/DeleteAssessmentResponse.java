package ru.shestakova.api.response.text;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.shestakova.api.model.text.Assessment;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class DeleteAssessmentResponse {

  Status status;
  Assessment assessment;

  public enum Status {
    SUCCESS,
    INITIATOR_NOT_FOUND,
    ASSESSMENT_NOT_FOUND;
  }
}
