package com.techeer.port.voilio.domain.user.batch;

import com.techeer.port.voilio.domain.email.EmailService;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.domain.user.service.UserService;
import javax.persistence.EntityManagerFactory;
import com.techeer.port.voilio.global.common.YnType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UserConfiguration {

  private final EmailService emailService;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final UserRepository userRepository;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final EntityManagerFactory entityManagerFactory;

  @Bean
  public Job sleeperUserJob() throws Exception {
    return this.jobBuilderFactory
        .get("sleeperUserJob")
        .incrementer(new RunIdIncrementer())
        //                .start(this.saveUserStep())
        .start(this.sleeperUserStep())
        //                .next(this.sleeperUserStep())
        .build();
  }

  @Bean
  public Step saveUserStep() {
    return this.stepBuilderFactory
        .get("saveUserStep")
        .tasklet(new SaveUserTasklet(userRepository, passwordEncoder))
        .build();
  }

  @Bean
  public Step sleeperUserStep() throws Exception {
    return this.stepBuilderFactory
        .get("sleeperUserStep")
        .<User, User>chunk(100)
        .reader(userReader())
        .processor(userProcessor())
        .writer(userWriter())
        .build();
  }

  private ItemReader<? extends User> userReader() throws Exception {
    JpaPagingItemReader<User> userReader =
        new JpaPagingItemReaderBuilder<User>()
            .queryString("select u from User u")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(100)
            .name("userItemReader")
            .build();

    userReader.afterPropertiesSet();
    return userReader;
  }

  private ItemProcessor<? super User, ? extends User> userProcessor() {
    return user -> {
      if (userService.checkSleeperUser(user) && user.getIsStopped().equals(YnType.N)) {
        return user;
      }
      return null;
    };
  }

  private ItemWriter<? super User> userWriter() {
    return users -> {
      users.forEach(
          x -> {
            x.changeSleeperUser();
            emailService.sendEmailToSleeperUser(x);
            userRepository.save(x);
          });
    };
  }
}
