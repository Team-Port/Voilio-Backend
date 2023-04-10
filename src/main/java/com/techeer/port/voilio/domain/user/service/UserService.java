package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.user.dto.request.UserRequest;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.exception.InvalidPassword;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.config.security.JwsToken;
import com.techeer.port.voilio.global.config.security.JwtProvider;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private AuthenticationManagerBuilder authenticationManagerBuilder;

  public void registerUser(UserRequest userRequest) throws Exception {
    String password = userRequest.getPassword();
    String encodePassword = passwordEncoder.encode(password);
    userRequest.setUserPassword(encodePassword);

    User user = userRequest.toEntity();
    userRepository.save(user);
  }

  public List<User> getUserList() {
    return new ArrayList<User>(userRepository.findAll());
  }

  public User getUser(Long userId) {
    User user = userRepository.findUserById(userId).orElseThrow(NotFoundUser::new);
    return user;
  }

  public User getUser(String email) {
    User user = userRepository.findUserByEmail(email).orElseThrow(NotFoundUser::new);
    return user;
  }

  public void deleteUser(Long userId) {
    User user = getUser(userId);
    user.changeDeleted();
    userRepository.save(user);
  }

    @Transactional
    public String login(String email, String password) {
      User user = getUser(email);
      String encodedPwd = user.getPassword();

      if (!passwordEncoder.matches(password, encodedPwd)) {
        throw new InvalidPassword();
      }

      // Authentication 객체 생성
//      UsernamePasswordAuthenticationToken authenticationToken =
//          new UsernamePasswordAuthenticationToken(email, password);
//      Authentication authentication =
//          authenticationManagerBuilder.getObject().authenticate(authenticationToken);

      // 검증된 인증 정보로 JWT 토큰 생성
      String createdToken = jwtProvider.createToken(user.getUsername(), user.getRoles());

      return createdToken;
    }
}
