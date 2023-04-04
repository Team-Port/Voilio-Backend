package com.techeer.port.voilio.domain.user.controller;

import static com.techeer.port.voilio.global.result.ResultCode.USER_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.user.dto.request.UserRequest;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API Document")
@RequestMapping("api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    @Operation(summary = "회원 생성", description = "회원 생성 메서드입니다.")
    public ResponseEntity<ResultResponse> createUser(@Valid @RequestBody UserRequest userRequest)
            throws Exception {
        userService.registerUser(userRequest);
        ResultResponse<User> resultResponse = new ResultResponse<>(USER_REGISTRATION_SUCCESS);
        resultResponse.add(
                linkTo(methodOn(UserController.class).createUser(userRequest)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }
}
