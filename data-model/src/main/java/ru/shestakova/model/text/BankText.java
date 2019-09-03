package ru.shestakova.model.text;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import ru.shestakova.util.Timestampable;

@Entity
@Table
@Data @Wither @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = false)
public class BankText extends Timestampable {

  @Id @GeneratedValue
  @Column(name = "TextId", nullable = false, updatable = false)
  Integer textId;

  @Column(name = "Text", nullable = false)
  String text;
}
