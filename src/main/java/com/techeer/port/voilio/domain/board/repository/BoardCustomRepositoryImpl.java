package com.techeer.port.voilio.domain.board.repository;

import static com.techeer.port.voilio.domain.board.entity.QBoard.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.DateType;
import com.techeer.port.voilio.global.common.YnType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice.Local;
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

  @Override
  public Page<Board> findBoardByCategoryAndKeyword(
      Category category, DateType dateType, String keyword, YnType delYn, YnType isPublic,
      Pageable pageable) {

    LocalDateTime currentDate = LocalDateTime.now();
    LocalDateTime startDate = null;

    switch (dateType) {
      case ONEHOUR:
        startDate = currentDate.minusHours(1);
        break;
      case ONEDAY:
        startDate = currentDate.minusDays(1);
        break;
      case ONEWEEK:
        startDate = currentDate.minusWeeks(1);
        break;
      case ONEMONTH:
        startDate = currentDate.minusMonths(1);
        break;
      case ONEYEAR:
        startDate = currentDate.minusYears(1);
        break;
      default:
        throw new IllegalArgumentException("잘못된 요청");
    }

    List<Board> boardList =
        jpaQueryFactory
            .selectFrom(board)
            .where(
                board.title.contains(keyword),
                board.category1.eq(category).or(board.category2.eq(category)),
                board.delYn.eq(delYn),
                board.isPublic.eq(isPublic),
                board.createAt.between(startDate, currentDate))
            .orderBy(board.createAt.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

    Long count =
        jpaQueryFactory
            .select(board.count())
            .from(board)
            .where(
                board.title.contains(keyword),
                board.category1.eq(category).or(board.category2.eq(category)),
                board.delYn.eq(delYn),
                board.isPublic.eq(isPublic),
                board.createAt.between(startDate, currentDate))
            .fetchFirst();

    return new PageImpl<>(boardList, pageable, count);
  }
}
