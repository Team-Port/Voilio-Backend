package com.techeer.port.voilio.domain.user.batch;

import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveUserTasklet implements Tasklet {
    private final UserRepository userRepository;

    public SaveUserTasklet(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<User> users = createDummyUsers();
        Collections.shuffle(users);
        userRepository.saveAll(users);

        return RepeatStatus.FINISHED;
    }

    private List<User> createDummyUsers() {
        List<User> users = new ArrayList<>();

        for (int i = 0 ; i< 100; i++) {
            users.add(User.builder()
                    .email("test@" + i)
                    .password("testPwd@" + i)
                    .nickname("testNick@" + i)
                    .activatedAt(LocalDateTime.now())
                    .build());
        }

        for (int i = 100 ; i< 200; i++) {
            users.add(User.builder()
                    .email("test@" + i)
                    .password("testPwd@" + i)
                    .nickname("testNick@" + i)
                    .activatedAt(LocalDateTime.now().minusDays(100))
                    .build());
        }

        for (int i = 200 ; i< 300; i++) {
            users.add(User.builder()
                    .email("test@" + i)
                    .password("testPwd@" + i)
                    .nickname("testNick@" + i)
                    .activatedAt(LocalDateTime.now().minusDays(200))
                    .build());
        }

        for (int i = 300 ; i< 400; i++) {
            users.add(User.builder()
                    .email("test@" + i)
                    .password("testPwd@" + i)
                    .nickname("testNick@" + i)
                    .activatedAt(LocalDateTime.now().minusYears(1))
                    .build());
        }

        for (int i = 400 ; i< 500; i++) {
            users.add(User.builder()
                    .email("test@" + i)
                    .password("testPwd@" + i)
                    .nickname("testNick@" + i)
                    .activatedAt(LocalDateTime.now().minusDays(500))
                    .build());
        }

        return null;
    }
}
