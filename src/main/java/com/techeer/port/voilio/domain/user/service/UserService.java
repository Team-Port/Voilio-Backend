package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.user.dto.request.UserRequest;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserRequest userRequest) throws Exception{
        String password = userRequest.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        userRequest.setUserPassword(encodePassword);

        User user = userRequest.toEntity();
        userRepository.save(user);
    }


}
