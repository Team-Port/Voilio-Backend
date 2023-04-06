package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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

  Page<Board> findAllByIsDeletedAndIsPublicAndCategory1OrCategory2OrderByCreateAtDesc(
      Boolean isDeleted,
      Boolean isPublic,
      Category category1,
      Category category2,
      Pageable pageable);
}
