package ru.shestakova.api.model.text;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssessmentMark {
  EXCELLENT(5),
  GOOD(4),
  SUFFICIENT(3),
  INSUFFICIENT(2),
  POOR(1);

  int value;

  private static Map<Integer, AssessmentMark> valueToMark =
      Stream.of(AssessmentMark.values()).collect(Collectors.toMap(
          AssessmentMark::getValue,
          mark -> mark)
      );

  public static AssessmentMark fromNumeric(int value) {
    if(!valueToMark.containsKey(value)) {
      throw new IllegalArgumentException("Unknown mark numeric: " + value);
    } else {
      return valueToMark.get(value);
    }
  }
}
