package com.techeer.port.voilio.domain.email;

import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private UserService userService;
  private final JavaMailSender javaMailSender;

  public void sendEmailToSleeperUser(User user) {
    Email email =
        Email.builder()
            .addressee(user.getEmail())
            .title("[ Voilio ] 휴면 계정 안내")
            .context("로그인 한 지 1년이 넘은 관계로, 휴면 계정으로 전환되었습니다.")
            .build();
  }
}
