package ru.shestakova.api.response.user;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class AuthenticationResponse {

  Status status;
  Long userId;
  UUID token;

  public enum Status {
    SUCCESS,
    USER_NOT_FOUND,
    WRONG_CREDENTIALS;

    public boolean isSuccess() {
      return this == SUCCESS;
    }
  }
}
