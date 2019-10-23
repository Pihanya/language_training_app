package ru.shestakova.api.response.forum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.shestakova.api.model.user.ServiceUser;
import ru.shestakova.api.model.user.UserDetails;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class RegistrationResponse {

  Status status;
  ServiceUser user;
  UserDetails details;

  public enum Status {
    SUCCESS,
    USERNAME_ALREADY_EXISTS;
  }
}
