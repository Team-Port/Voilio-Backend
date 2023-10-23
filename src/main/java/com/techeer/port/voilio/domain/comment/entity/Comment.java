package com.techeer.port.voilio.domain.comment.entity;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.comment.dto.request.CommentUpdateRequest;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.YnType;
import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseEntity {

  @Id
  @Column(name = "comment_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Enumerated(EnumType.STRING)
  private YnType delYn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_comment_id")
  private Comment parentComment;

  @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> childComments = new ArrayList<>();

  public void updateComment(CommentUpdateRequest commentUpdateRequest) {
    this.content = commentUpdateRequest.getContent();
  }

  public void deleteComment() {
    this.delYn = YnType.Y;
  }


  public void updateParentComment(Comment parentComment) {
    this.parentComment = parentComment;
  }
}