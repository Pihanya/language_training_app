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
import lombok.experimental.FieldDefaults;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;
import ru.shestakova.util.Timestampable;

@Entity @Table
@Repository
@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = false)
public class TextWork extends Timestampable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "WorkId", nullable = false, updatable = false)
  Long workId;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "TextId", nullable = false, updatable = false)
  BankText text;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "AuthorId", nullable = false, updatable = false)
  ServiceUser author;

  @Column(name = "Type", nullable = false, updatable = false)
  Integer type;

  @Column(name = "WorkTitle", nullable = false)
  String workTitle;

  @Column(name = "WorkText", nullable = false)
  String workText;

  @Column(name = "Rating", nullable = false)
  Integer rating;
}