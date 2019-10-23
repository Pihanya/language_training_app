package ru.shestakova.api.response.forum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.shestakova.api.model.forum.MessageTerminationStatus;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class TerminateMessageResponse {

  Status status;
  MessageTerminationStatus previousStatus;
  MessageTerminationStatus actualStatus;

  public enum Status {
    SUCCESS,
    INITIATOR_NOT_FOUND,
    THEME_NOT_FOUND,
    MESSAGE_NOT_FOUND,
    FORBIDDEN_TRANSITION;

    public boolean isSuccess() {
      return this == SUCCESS;
    }
  }
}
