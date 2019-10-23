package ru.shestakova.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class BankText extends Timestampable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TextId", insertable = false, nullable = false, updatable = false)
  Integer textId;

  @Column(name = "Title", nullable = false)
  String title;

  @Column(name = "Text", nullable = false)
  String text;

  @ManyToOne
  @JoinColumn(name = "AuthorId", nullable = false, updatable = false)
  ServiceUser author;
}
