package com.techeer.port.voilio.domain.comment.entity;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.comment.dto.request.CommentUpdateRequest;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.techeer.port.voilio.global.common.YnType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private YnType delYn;

  @Builder
  private Comment(String content, Board board, User user) {
    this.content = content;
    this.board = board;
    this.user = user;
  }

  public void updateComment(CommentUpdateRequest commentUpdateRequest) {
    this.content = commentUpdateRequest.getContent();
  }

  public void deleteComment() {
    this.delYn = YnType.Y;
  }
}
