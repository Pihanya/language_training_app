package ru.shestakova.model.user;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

@Entity
@Table
@Data @Wither @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class Permission {

  @Id @GeneratedValue
  @Column(name = "PermissionId", nullable = false, updatable = false)
  Short permissionId;

  @Column(name = "Name", unique = true, nullable = false)
  String name;

  @Column(name = "Description")
  String description;

  @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
  @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) @Wither(AccessLevel.NONE)
  Set<UserPermission> permissions;
}
