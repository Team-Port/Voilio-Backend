package com.techeer.port.voilio.global.common;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @CreatedDate
  @Column(name = "createdAt", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updatedAt", nullable = false, updatable = false)
  private LocalDateTime updatedAt;

  @Column
  @NotNull
  @ColumnDefault("true")
  private boolean isDeleted;

  public boolean getIsDeleted() {
    return this.getIsDeleted();
  }
}
