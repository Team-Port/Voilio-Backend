package com.techeer.port.voilio.domain.board.repository;

import static com.techeer.port.voilio.domain.board.entity.QBoard.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.YnType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<Board> findBoardByKeyword(
      String keyword, YnType delYn, YnType isPublic, Pageable pageable) {
    List<Board> boardList =
        jpaQueryFactory
            .selectFrom(board)
            .where(
                board.title.contains(keyword), board.delYn.eq(delYn), board.isPublic.eq(isPublic))
            .orderBy(board.createAt.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

    Long count =
        jpaQueryFactory
            .select(board.count())
            .from(board)
            .where(
                board.title.contains(keyword), board.delYn.eq(delYn), board.isPublic.eq(isPublic))
            .fetchFirst();

    return new PageImpl<>(boardList, pageable, count);
  }
}
