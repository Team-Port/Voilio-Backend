package com.techeer.port.voilio.domain.email;

import com.techeer.port.voilio.domain.user.entity.User;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender javaMailSender;
  private final TemplateEngine templateEngine;

  public void sendEmailToSleeperUser(User user) {
    Email email = Email.builder().address(user.getEmail()).title("[ Voilio ] 휴면 계정 안내").build();

    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(email.getAddress());
      helper.setSubject(email.getTitle());

      Context context = new Context();
      context.setVariable("nickname", user.getNickname());

      String htmlContent = templateEngine.process("email/emailTemplate", context);
      helper.setText(htmlContent, true);

      javaMailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
