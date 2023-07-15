package com.techeer.port.voilio.domain.comment.repository;

import static com.techeer.port.voilio.domain.comment.entity.QComment.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Comment> findByBoardId(Long id) {
    return queryFactory
        .select(comment)
        .join(comment.board)
        .fetchJoin()
        .where(comment.isDeleted.eq(false), comment.board.id.eq(id))
        .fetch();
  }
}
