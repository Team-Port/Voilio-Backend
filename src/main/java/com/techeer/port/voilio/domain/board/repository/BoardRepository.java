package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.techeer.port.voilio.global.result.ResultResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
  List<Board> findAllByIsDeletedAndIsPublic(Boolean isDeleted, Boolean isPublic);

  List<Board> findAllByTitleContaining(String keyword);

  List<Board> findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(String keyword);

  Optional<Board> findByIdAndIsDeletedFalseAndIsPublicTrue(Long boardId);

  Optional<Board> findByIdAndIsDeleted(Long boardId, Boolean isDeleted);

  Page<Board> findAllByIsDeletedAndIsPublicOrderByCreateAtDesc(Boolean isDeleted, Boolean isPublic, Pageable pageable);
}
