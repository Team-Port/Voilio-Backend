package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.YnType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findUserByEmailAndDelYn(email, YnType.N)
        .map(this::createUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(email + " 을 DB에서 찾을 수 없습니다"));
  }

  private UserDetails createUserDetails(User user) {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

    return new org.springframework.security.core.userdetails.User(
        String.valueOf(user.getId()), user.getPassword(), Collections.singleton(grantedAuthority));
  }
}
