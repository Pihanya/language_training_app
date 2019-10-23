package ru.shestakova.api.response.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.shestakova.api.model.user.ServiceUser;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class GetServiceUserResponse {

  Status status;
  ServiceUser user;

  public enum Status {
    SUCCESS,
    USER_NOT_FOUND;

    public boolean isSuccess() {
      return this == SUCCESS;
    }
  }
}
