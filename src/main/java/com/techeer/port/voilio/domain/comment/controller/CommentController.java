package com.techeer.port.voilio.domain.comment.controller;

import static com.techeer.port.voilio.global.result.ResultCode.COMMENT_CREATED_SUCCESS;
import static com.techeer.port.voilio.global.result.ResultCode.UPDATE_COMMENT_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.comment.dto.request.CommentInfo;
import com.techeer.port.voilio.domain.comment.dto.request.CommentRequest;
import com.techeer.port.voilio.domain.comment.dto.request.CommentUpdateRequest;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.domain.comment.service.CommentService;
import com.techeer.port.voilio.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "Comment API Document")
@RequestMapping("/api/v1/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @Operation(summary = "댓글 생성 ", description = "댓글 생성 메서드입니다.")
  @PostMapping
  public ResponseEntity<ResultResponse> createComment(
      @Valid @RequestBody CommentRequest commentRequest) {

    CommentInfo commentInfo = commentService.registerComment(commentRequest);

    ResultResponse<Comment> resultResponse =
        new ResultResponse<>(COMMENT_CREATED_SUCCESS, commentInfo);
    resultResponse.add(
        linkTo(methodOn(CommentController.class).createComment(commentRequest)).withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }

  @Operation(summary = "댓글 수정", description = "댓글 수정 메서드입니다.")
  @PutMapping("/{commentId}")
  public ResponseEntity<ResultResponse> updateComment(
      @Valid @RequestBody CommentUpdateRequest commentUpdateRequest, @PathVariable Long commentId) {

    CommentInfo commentInfo = commentService.updateComment(commentUpdateRequest, commentId);

    ResultResponse<Comment> resultResponse =
        new ResultResponse<>(UPDATE_COMMENT_SUCCESS, commentInfo);
    resultResponse.add(
        linkTo(methodOn(CommentController.class).updateComment(commentUpdateRequest, commentId))
            .withSelfRel());

    return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
  }
}
