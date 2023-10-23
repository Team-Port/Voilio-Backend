package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.YnType;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    if (email == null || email.equals("")) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND_ERROR);
    }

    Optional<User> optionalUser = userRepository.findUserByEmailAndDelYn(email, YnType.N);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    }
    throw new BusinessException(ErrorCode.USER_NOT_FOUND_ERROR);
  }
}
