package ru.shestakova.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import ru.shestakova.util.Timestampable;

@Entity @Table
@Repository
@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = false)
public class ForumMessage extends Timestampable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MessageId", insertable = false, updatable = false)
  Long messageId;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "AuthorId", nullable = false, updatable = false)
  ServiceUser author;

  @Column(name = "MessageText", nullable = false)
  String text;

  @Column(name = "TerminationStatus", nullable = false)
  Integer terminationStatus;

  @Column(name = "DeleteDate")
  Long deleteDate;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "ThemeId", updatable = false)
  ForumTheme theme;
}
