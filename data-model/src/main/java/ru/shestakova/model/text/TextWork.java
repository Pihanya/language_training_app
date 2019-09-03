package ru.shestakova.model.text;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class TextWork extends Timestampable {

  @Id @GeneratedValue
  @Column(name = "WorkId", nullable = false, updatable = false)
  Long workId;

  @Column(name = "WorkText", nullable = false)
  String workText;

  @Column(name = "Rating", nullable = false)
  Integer rating;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "TextId", nullable = false, updatable = false)
  BankText text;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "AuthorId", nullable = false, updatable = false)
  ServiceUser author;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "TypeId", nullable = false, updatable = false)
  WorkType type;
}