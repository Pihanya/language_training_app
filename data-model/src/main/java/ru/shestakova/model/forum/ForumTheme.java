package ru.shestakova.model.forum;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import ru.shestakova.model.user.ServiceUser;
import ru.shestakova.util.Timestampable;

@Entity
@Table
@Data @Wither @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = false)
public class ForumTheme extends Timestampable {

  @Id @GeneratedValue
  @Column(name = "ThemeId", nullable = false, updatable = false)
  Integer themeId;

  @Column(name = "ThemeName", nullable = false)
  String themeName;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "AuthorId", nullable = false, updatable = false)
  ServiceUser author;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "theme", fetch = FetchType.LAZY)
  @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) @Wither(AccessLevel.PROTECTED)
  Set<ForumMessage> messages;
}
