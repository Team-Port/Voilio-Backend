package com.techeer.port.voilio.domain.comment.controller;

import com.techeer.port.voilio.domain.comment.dto.request.CommentRequest;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.domain.comment.service.CommentService;
import com.techeer.port.voilio.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import static com.techeer.port.voilio.global.result.ResultCode.COMMENT_CREATED_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Comment", description = "Comment API Document")
@RequestMapping("/api/v1/comments")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentController {

  private final CommentService commentService;

  @Operation(summary = "댓글 생성 ", description = "댓글 생성 메서드입니다.")
  @PostMapping
  public ResponseEntity<ResultResponse> createComment(
      @Valid @RequestBody CommentRequest commentRequest) {

    commentService.registerComment(commentRequest);

    ResultResponse<Comment> resultResponse = new ResultResponse<>(COMMENT_CREATED_SUCCESS);
    resultResponse.add(
        linkTo(methodOn(CommentController.class).createComment(commentRequest)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }
}
