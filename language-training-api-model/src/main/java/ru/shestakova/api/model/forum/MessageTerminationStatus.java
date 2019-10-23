package ru.shestakova.api.model.forum;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE) @AllArgsConstructor
public enum MessageTerminationStatus {
  EXISTS(0),
  IN_CLOSED_THEME(1),
  IN_ARCHIVED_THEME(2),
  IN_DELETED_THEME(3),
  DELETED(4);

  int value;

  private static Map<Integer, MessageTerminationStatus> numericToStatus = Stream
      .of(MessageTerminationStatus.values())
      .collect(
          Collectors.toMap(
              terminationStatus -> terminationStatus.value,
              terminationStatus -> terminationStatus
          )
      );

  public static MessageTerminationStatus fromNumeric(int value) {
    var status = numericToStatus.get(value);
    if (status == null) {
      throw new IllegalArgumentException("Unknown termination status: " + value);
    } else {
      return status;
    }
  }
}
