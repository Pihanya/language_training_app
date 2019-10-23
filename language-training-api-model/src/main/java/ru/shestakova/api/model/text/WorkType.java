package ru.shestakova.api.model.text;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum WorkType {
  ANALYSIS(0),
  TRANSLATION(1),
  COMMENT(2),
  RETELLING(3);

  private int value;

  private static Map<Integer, WorkType> codeToWorkType =
      Stream.of(WorkType.values()).collect(Collectors.toMap(
          WorkType::getValue,
          type -> type
      ));

  public static WorkType fromNumeric(int value) {
    var type = codeToWorkType.get(value);
    if(type == null) {
      throw new IllegalArgumentException("Unknown code: " + value);
    } else {
      return type;
    }
  }
}
