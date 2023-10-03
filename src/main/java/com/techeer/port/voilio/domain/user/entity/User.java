package com.techeer.port.voilio.domain.user.entity;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.YnType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  private String email;

  private String password;

  private String nickname;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "activated_at")
  private LocalDateTime activatedAt;

  @Column(name = "is_stopped")
  @ColumnDefault("'N'")
  @Enumerated(EnumType.STRING)
  private YnType isStopped;

  @OneToMany(
      mappedBy = "user",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Board> boards;

  @Enumerated(EnumType.STRING)
  private Authority authority;

  @Enumerated(EnumType.STRING)
  @ColumnDefault("'N'")
  private YnType delYn;

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

  public void updateActivatedAt(LocalDateTime activatedAt) {
    this.activatedAt = activatedAt;
  }

  public void setStopped(YnType stopped) {
    isStopped = stopped;
  }

  public void changeSleeperUser() {
    this.setStopped(YnType.Y);
  }

  public void changeDelYn(YnType delYn) {
    this.delYn = delYn;
  }
}
