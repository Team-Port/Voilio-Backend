package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.BoardDivision;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.YnType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

  @Query("SELECT b FROM Board b WHERE b.id = :board_id AND b.delYn = 'N' AND b.isPublic = 'Y'")
  Optional<Board> findBoardById(@Param("board_id") Long id);

  @Query("SELECT b FROM Board b WHERE b.id = :board_id AND b.delYn = 'N'")
  Optional<Board> findBoardByIdExceptHide(@Param("board_id") Long id);

  Page<Board> findAllByDelYnAndIsPublicOrderByUpdateAtDesc(
      Pageable pageable, YnType delYn, YnType isPublic);

  Page<Board> findBoardsByDelYnAndIsPublicAndUserOrderByUpdateAtDesc(
      Pageable pageable, YnType delYn, YnType isPublic, User user);

  Page<Board> findBoardsByDelYnAndUserOrderByUpdateAtDesc(
      Pageable pageable, YnType delYn, User user);

  Page<Board> findByDelYnAndIsPublicAndCategory1OrCategory2OrderByCreateAtDesc(
      YnType delYn, YnType isPublic, Category category1, Category category2, Pageable pageable);

  @Query(
      "SELECT b FROM Board b WHERE b.delYn = 'N' AND b.user.nickname ="
          + " :nickname ORDER BY b.createAt DESC")
  Page<Board> findBoardByUserNicknameExceptHide(
      @Param("nickname") String nickname, Pageable pageable);

  Long countBoardByUserAndDivision(User user, BoardDivision boardDivision);
}
