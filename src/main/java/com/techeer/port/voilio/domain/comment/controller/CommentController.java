package com.techeer.port.voilio.domain.comment.controller;

import static com.techeer.port.voilio.global.result.ResultCode.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.comment.dto.CommentDto;
import com.techeer.port.voilio.domain.comment.dto.request.CommentInfo;
import com.techeer.port.voilio.domain.comment.dto.request.CommentRequest;
import com.techeer.port.voilio.domain.comment.dto.request.CommentUpdateRequest;
import com.techeer.port.voilio.domain.comment.dto.response.CommentResponse;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.domain.comment.service.CommentService;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import com.techeer.port.voilio.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "Comment API Document")
@RequestMapping("/api/v1/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성 ", description = "댓글 및 대댓글 생성")
    @PostMapping
    public ResponseEntity<ResultResponse<CommentDto>> createComment(
            @RequestBody CommentRequest commentRequest
            , @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        CommentDto commentDto = commentService.createComment(commentRequest, user);
        ResultResponse<CommentDto> resultResponse =
                new ResultResponse<>(COMMENT_CREATED_SUCCESS, commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    @Operation(summary = "댓글 수정", description = "댓글 및 대댓글 수정 (본인만 가능)")
    @PutMapping("/{commentId}")
    public ResponseEntity<ResultResponse<CommentDto>> updateComment(
            @RequestBody CommentUpdateRequest commentUpdateRequest
            , @PathVariable Long commentId
            , @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        CommentDto commentDto = commentService.updateComment(commentUpdateRequest, commentId, user);
        ResultResponse<CommentDto> resultResponse =
                new ResultResponse<>(UPDATE_COMMENT_SUCCESS, commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    @Operation(summary = "댓글 삭제", description = "댓글 삭제 (soft delete)")
    @PatchMapping("/{commentId}")
    public ResponseEntity<ResultResponse> deleteComment(
            @PathVariable Long commentId
            , @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        commentService.deleteComment(commentId, user);
        ResultResponse resultResponse = new ResultResponse<>(DELETE_COMMENT_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    @Operation(summary = "게시글 별 댓글 및 대댓글 조회")
    @GetMapping("/{boardId}/list")
    public ResponseEntity<ResultResponse> getCommentList(@PathVariable Long boardId) {
        List<CommentDto> commentList = commentService.findCommentByBoardId(boardId);
        ResultResponse<Comment> resultResponse = new ResultResponse<>(GET_COMMENT_SUCCESS, commentList);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }
}
