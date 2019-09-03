package ru.shestakova.model.text;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
public class Assessment extends Timestampable {

  @Id @GeneratedValue
  @Column(name = "AssessmentId", nullable = false, updatable = false)
  Long assessmentId;

  @OneToOne(optional = false)
  @JoinColumn(name = "MarkNum", nullable = false)
  AssessmentMark mark;

  @Column(name = "Comment", nullable = false)
  String comment;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "WorkId", nullable = false, updatable = false)
  TextWork work;

  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "ExpertId", nullable = false, updatable = false)
  ServiceUser expert;
}
