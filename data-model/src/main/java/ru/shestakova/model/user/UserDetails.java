package ru.shestakova.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import ru.shestakova.util.Timestampable;

@Entity
@Table
@Data @Wither @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = false)
public class UserDetails extends Timestampable {

  @Id
  @Column(name = "UserId", nullable = false, updatable = false)
  Long userId;

  @ManyToOne(optional = false)
  @JoinColumn(name = "LevelNum", nullable = false)
  UserLevel level;

  @Column(name = "ScreenName")
  String screenName;

  @Column(name = "Rating", nullable = false)
  Integer rating;

  @Column(name = "Bio")
  String bio;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId
  @ToString.Exclude @EqualsAndHashCode.Exclude
  ServiceUser user;
}

