package com.techeer.port.voilio.domain.user.entity;

import com.techeer.port.voilio.global.common.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @NotBlank @Column private String email;

  @NotBlank @Column private String password;

  @NotBlank @Column private String nickname;

  @Builder
  private User(Long id, String email, String password, String nickname) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.nickname = nickname;
  }
}
