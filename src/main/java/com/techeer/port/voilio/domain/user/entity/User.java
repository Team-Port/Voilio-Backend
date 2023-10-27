package com.techeer.port.voilio.domain.user.entity;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.YnType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

  @Column(name = "del_yn")
  @Enumerated(EnumType.STRING)
  private YnType delYn;

  public void updateActivatedAt(LocalDateTime activatedAt) {
    this.activatedAt = activatedAt;
  }

  public void changeSleeperUser() {
    this.changeIsStopped(YnType.Y);
  }

  public void changePassword(String password) {
    this.password = password;
  }

  public void changeUserRole(Authority roleType) {
    this.authority = roleType;
  }

  public void changeDelYn(YnType ynType) {
    this.delYn = ynType;
  }

  public void changeIsStopped(YnType ynType) {
    this.isStopped = ynType;
  }

  public void changeImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    if (email != null && !email.equals("")) {
      return email;
    } else {
      return id.toString();
    }
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
}
