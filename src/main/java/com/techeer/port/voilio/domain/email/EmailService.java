package com.techeer.port.voilio.domain.email;

import com.techeer.port.voilio.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender javaMailSender;
  private final TemplateEngine templateEngine;

  public void sendEmailToSleeperUser(User user) {
    Email email =
            Email.builder()
                    .addressee(user.getEmail())
                    .title("[ Voilio ] 휴면 계정 안내")
                    .build();

    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(email.getAddressee());
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
