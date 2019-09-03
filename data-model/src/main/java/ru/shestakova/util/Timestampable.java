package ru.shestakova.util;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data @FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Timestampable implements Serializable {

  @Column(name = "CreationDate", updatable = false)
  Date creationDate;

  @Column(name = "EditDate")
  Date editDate;

  @Version
  @Column(name = "Version")
  Integer version;

  public void update() {
    updatedAt();
  }

  @PrePersist
  void createdAt() {
    this.creationDate = this.editDate = new Date();
    this.version = 0;
  }

  @PreUpdate
  void updatedAt() {
    this.editDate = new Date();
    this.version += 1;
  }
}
