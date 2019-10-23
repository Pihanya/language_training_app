package ru.shestakova.api.model.user;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE)
public enum UserRole {
  USER(
      "user", 0,
      true, true, false,false, true, false,

      false, false,false, false, false,

      true, true, true,

      false, false,

      true, true, true,

      false, false,

      false, false, false,

      false, false,

      false, false, false,

      false, false
  ),

  EXPERT(
      "expert", 1,

      true, true, false,false, true, false,

      false, false,true, false, false,

      true, true, true,

      false, false,

      true, true, true,

      false, false,

      false, false, false,

      false, false,

      true, true, true,

      false, false
  ),

  ADMIN(
        "admin", 2,

      true, true, true,true, true, true,

      true, true,true, true, true,

      true, true, true,

      true, true,

      true, true, true,

      true, true,

      true, true, true,

      true, true,

      true, true, true,

      true, true
  );

  String roleId;

  int roleLevel;

  boolean canCreateThemes;
  boolean canEditHisThemes;
  boolean canOpenHisThemes;
  boolean canCloseHisThemes;
  boolean canDeleteHisThemes;
  boolean canArchiveHisThemes;

  boolean canEditOthersThemes;
  boolean canOpenOthersThemes;
  boolean canCloseOthersThemes;
  boolean canDeleteOthersThemes;
  boolean canArchiveOthersThemes;

  boolean canCreateMessages;
  boolean canEditHisMessages;
  boolean canDeleteHisMessages;

  boolean canEditOthersMessages;
  boolean canDeleteOthersMessages;

  boolean canCreateWorks;
  boolean canEditHisWorks;
  boolean canDeleteHisWorks;

  boolean canEditOthersWorks;
  boolean canDeleteOthersWorks;

  boolean canCreateTexts;
  boolean canEditHisTexts;
  boolean canDeleteHisTexts;

  boolean canEditOthersTexts;
  boolean canDeleteOtherTexts;

  boolean canCreateAssessments;
  boolean canEditHisAssessments;
  boolean canDeleteHisAssessments;

  boolean canEditOthersAssessments;
  boolean canDeleteOthersAssessments;

  private static Map<String, UserRole> numericToRole =
      Stream.of(UserRole.values()).collect(Collectors.toMap(
          UserRole::getRoleId,
          role -> role
      ));

  public static UserRole fromId(String value) {
    value = value.toLowerCase().strip();
    if (!numericToRole.containsKey(value)) {
      throw new IllegalArgumentException("Unknown role id: " + value);
    } else {
      return numericToRole.get(value);
    }
  }

  public int compare(UserRole other) {
    return this.roleLevel - other.roleLevel;
  }
}
