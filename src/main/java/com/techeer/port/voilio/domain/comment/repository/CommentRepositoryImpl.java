package com.techeer.port.voilio.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.techeer.port.voilio.domain.comment.entity.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findByBoardId(Long id) {
        return queryFactory.select(comment)
                .join(comment.board).fetchJoin()
                .where(comment.isDeleted.eq(false), comment.board.id.eq(id))
                .fetch();
    }
}
