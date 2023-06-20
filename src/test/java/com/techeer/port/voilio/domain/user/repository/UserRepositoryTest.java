package com.techeer.port.voilio.domain.user.repository;

import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.user.entity.Authority;
import com.techeer.port.voilio.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.techeer.port.voilio.domain.user.entity.Authority.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    List<User> users = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        users.add(userRepository.save(User.builder()
                .email("test@example.com")
                .password("testPassword")
                .nickname("tester")
                .authority(ROLE_USER).build()));

        users.add(userRepository.save(User.builder()
                .email("test2@example.com")
                .password("testPassword")
                .nickname("tester2")
                .authority(ROLE_USER).build()));
    }

    @Nested
    @DisplayName("User 찾기")
    class findUser {
        @Test
        @DisplayName("존재하는 Id로 찾기")
        public void findUserByIdWhenExist() {
            //given
            User expectUser = users.get(0);

            //when
            User findedUser = userRepository.findUserById(1).orElseThrow();

            //then
            assertEquals(expectUser.getId(),findedUser.getId());
            assertEquals(expectUser.getEmail(),findedUser.getEmail());
        }

        @Test
        @DisplayName("존재하지 않는 또는 삭제된 Id로 찾기")
        public void findUserByIdWhenNotExistOrDelete(){
            //given
            int index = 1;
            User user = userRepository.findUserById(index).orElseThrow();
            user.changeDeleted();
            userRepository.save(user);

            //when,then
            Optional<User> findUser = userRepository.findUserById(index);
            assertFalse(findUser.isEmpty());

        }
    }


}
