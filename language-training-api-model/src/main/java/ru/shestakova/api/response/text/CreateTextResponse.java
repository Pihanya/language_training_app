package ru.shestakova.api.response.text;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.shestakova.api.model.text.BankText;

@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class CreateTextResponse {

  Status status;
  BankText text;

  public enum Status {
    SUCCESS,
    INITIATOR_NOT_FOUND
  }
}
