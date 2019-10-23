package ru.shestakova.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;
import ru.shestakova.util.Timestampable;

@Entity @Table
@Repository
@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = false)
public class Assessment extends Timestampable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "AssessmentId", nullable = false, updatable = false)
  Long assessmentId;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "WorkId", nullable = false, updatable = false)
  TextWork work;

  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "ExpertId", nullable = false, updatable = false)
  ServiceUser expert;

  @Column(name = "MarkRating", nullable = false)
  Integer markRating;

  @Column(name = "Comment", nullable = false)
  String comment;
}
