package com.techeer.port.voilio.domain.user.dto.request;

import com.techeer.port.voilio.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.NotNull;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;


@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserRequest {
    private Long id;
    private String email;
    private String password;
    private String nickname;

    public User toEntity(){
        return User.builder()
                .id(id)
                .email(email)
//                .password(new BCryptPasswordEncoder().encode(password))
                .password(password)
                .nickname(nickname)
                .build();
    }
}
