package ru.shestakova.model.user;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table
@Data @Wither @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class UserPermission {

  @EmbeddedId
  UserPermissionKey id;

  @ManyToOne(optional = false)
  @MapsId("UserId")
  ServiceUser user;

  @ManyToOne(optional = false)
  @MapsId("PermissionId")
  Permission permission;

  @CreatedDate
  @Column(name = "GrandDate", nullable = false, updatable = false)
  Date grantDate;

  @PrePersist
  public void prePersist() {
    this.grantDate = new Date();
  }

  @Embeddable
  @Data @Wither @FieldDefaults(level = AccessLevel.PRIVATE)
  @NoArgsConstructor @AllArgsConstructor
  public static class UserPermissionKey implements Serializable {

    @Column(name = "UserId", nullable = false, updatable = false)
    Integer userId;

    @Column(name = "PermissionId", nullable = false, updatable = false)
    Short permissionId;
  }
}
