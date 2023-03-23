package com.techeer.port.voilio.global.common;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
  @CreatedDate private LocalDate createAt;

  @LastModifiedDate private LocalDate updateAt;

  @Column
  @NotNull
  @ColumnDefault("false")
  private boolean isDeleted;

  public boolean getIsDeleted() {
    return this.isDeleted;
  }

  public void changeDeleted() {
    this.isDeleted = !this.isDeleted;
  }
}
