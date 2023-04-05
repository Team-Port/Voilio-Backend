package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.user.dto.request.UserRequest;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.exception.NotFoundUserException;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

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

  public User getUserById (Long user_id) {
    User user =
            userRepository
                    .findUserById(user_id)
                    .orElseThrow(NotFoundUser::new);
    return user;
  }

}
