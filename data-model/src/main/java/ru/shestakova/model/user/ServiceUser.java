package ru.shestakova.model.user;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

@Entity
@Table
@Data @Wither @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class ServiceUser {

  @Id @GeneratedValue
  @Column(name = "UserId", unique = true, nullable = false, updatable = false, insertable = false)
  Long userId;

  @Column(name = "Username", unique = true, nullable = false, columnDefinition = "VARCHAR(40)")
  String username;

  @Column(name = "Email", unique = true)
  String email;

  @Column(name = "VKId", unique = true)
  Long vkId;

  @Column(name = "VKToken")
  String vkToken;

  @Column(name = "AvatarURL", nullable = false)
  String avatarURL;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", optional = false, fetch = FetchType.LAZY)
  UserDetails details;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @Setter(AccessLevel.NONE) @Wither(AccessLevel.NONE)
  @EqualsAndHashCode.Exclude @ToString.Exclude
  Set<UserPermission> permissions;
}
