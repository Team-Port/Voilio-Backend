package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
  List<Board> findAllByIsDeletedAndIsPublic(Boolean isDeleted, Boolean isPublic);

  List<Board> findAllByTitleContaining(String keyword);

  List<Board> findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(String keyword);

  Optional<Board> findByIdAndIsDeletedFalseAndIsPublicTrue(Long boardId);

  Optional<Board> findByIdAndIsDeleted(Long boardId, Boolean isDeleted);

  Page<Board> findAllByIsDeletedAndIsPublicOrderByCreateAtDesc(
      Boolean isDeleted, Boolean isPublic, Pageable pageable);

  @Query(
      "SELECT b FROM Board b WHERE b.isDeleted = false AND b.isPublic = true AND (b.category1 ="
          + " :category1 OR b.category2 = :category2) ORDER BY b.createAt DESC")
  Page<Board> findBoardByCategory(
      @Param("category1") Category category1,
      @Param("category2") Category category2,
      Pageable pageable);
}
