package com.techeer.port.voilio.domain.user.batch;

import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SaveUserTasklet implements Tasklet {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SaveUserTasklet(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    List<User> users = createDummyUsers();
    Collections.shuffle(users);
    userRepository.saveAll(users);

    return RepeatStatus.FINISHED;
  }

  private List<User> createDummyUsers() {
    List<User> users = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      users.add(
          User.builder()
              .email("test@" + i)
              .password(passwordEncoder.encode("testPwd@" + i))
              .nickname("testNick@" + i)
              .activatedAt(LocalDateTime.now())
              .build());
    }

    for (int i = 100; i < 200; i++) {
      users.add(
          User.builder()
              .email("test@" + i)
              .password(passwordEncoder.encode("testPwd@" + i))
              .nickname("testNick@" + i)
              .activatedAt(LocalDateTime.now().minusDays(100))
              .build());
    }

    for (int i = 200; i < 300; i++) {
      users.add(
          User.builder()
              .email("test@" + i)
              .password(passwordEncoder.encode("testPwd@" + i))
              .nickname("testNick@" + i)
              .activatedAt(LocalDateTime.now().minusDays(1))
              .build());
    }

    for (int i = 300; i < 400; i++) {
      users.add(
          User.builder()
              .email("test@" + i)
              .password(passwordEncoder.encode("testPwd@" + i))
              .nickname("testNick@" + i)
              .activatedAt(LocalDateTime.now().minusYears(2))
              .build());
    }

    return users;
  }
}
