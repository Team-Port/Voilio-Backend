package com.techeer.port.voilio.domain.comment.repository;

import static com.techeer.port.voilio.domain.comment.entity.QComment.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.global.common.YnType;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Comment> findByBoardId(Long id) {
    return queryFactory
        .selectFrom(comment)
        .join(comment.board)
        .fetchJoin()
        .where(comment.delYn.eq(YnType.N), comment.board.id.eq(id))
        .fetch();
  }
}
