package ru.shestakova.model.forum;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import ru.shestakova.model.user.ServiceUser;
import ru.shestakova.util.Timestampable;

@Entity
@Table
@Data @Wither @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = false)
public class ForumMessage extends Timestampable {
  @EmbeddedId
  ForumMessageKey id;

  @Column(name = "MessageText", nullable = false)
  String text;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "AuthorId", nullable = false, updatable = false)
  ServiceUser author;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "ThemeId", nullable = false, updatable = false)
  @MapsId("ThemeId")
  ForumTheme theme;

  @Embeddable
  @Data @Wither @FieldDefaults(level = AccessLevel.PRIVATE)
  @NoArgsConstructor @AllArgsConstructor
  public static class ForumMessageKey implements Serializable {

    @GeneratedValue
    @Column(name = "MessageId", nullable = false, updatable = false)
    Integer messageId;

    @Column(name = "ThemeId", nullable = false, updatable = false)
    Integer themeId;
  }
}
