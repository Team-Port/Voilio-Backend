package com.techeer.port.voilio.domain.user.entity;

import com.techeer.port.voilio.global.common.BaseEntity;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Builder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(nullable = false)
  @NotBlank
  private String email;

  @NotBlank
  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @NotBlank
  private String nickname;

  @Column(name = "activated_at")
  private LocalDateTime activatedAt;

  @Column(name = "is_stopped")
  @ColumnDefault("false")
  private boolean isStopped;

  @Enumerated(EnumType.STRING)
  private Authority authority;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

  public void setActivatedAt(LocalDateTime activatedAt) {
    this.activatedAt = activatedAt;
  }

  public void setStopped(boolean stopped) { isStopped = stopped; }

  public void changeSleeperUser() { this.setStopped(true); }
}
