package ru.shestakova.util;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Timestampable implements Serializable {

  @Column(name = "CreateDate", updatable = false)
  Long createDate;

  @Column(name = "EditDate")
  Long editDate;

  @Version
  @Column(name = "Version")
  Integer version;

  public void update() {
    updatedAt();
  }

  @PrePersist
  void createdAt() {
    if (this.createDate != null) {
      this.editDate = this.createDate;
    } else {
      this.createDate = this.editDate = Instant.now().toEpochMilli();
    }
    this.version = 0;
  }

  @PreUpdate
  void updatedAt() {
    this.editDate = Instant.now().toEpochMilli();
    this.version += 1;
  }
}
