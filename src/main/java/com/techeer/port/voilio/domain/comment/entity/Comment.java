package com.techeer.port.voilio.domain.comment.entity;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.BaseEntity;
import lombok.*;
import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString(callSuper = true)
@Table(name = "comments")
public class Comment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @Column(nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id", nullable = false)
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Builder
  private Comment(String content, Board board, User user) {
    this.content = content;
    this.board = board;
    this.user = user;
  }
}
