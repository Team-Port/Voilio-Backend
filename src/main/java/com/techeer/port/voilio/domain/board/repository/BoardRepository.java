package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
  Page<Board> findAllByIsDeletedAndIsPublicOrderByCreateAtDesc(
      Boolean isDeleted, Boolean isPublic, Pageable pageable);

  Optional<Board> findByIdAndIsDeleted(Long boardId, Boolean isDeleted);

  List<Board> findAllByIsDeletedAndIsPublic(Boolean isDeleted, Boolean isPublic);

  Optional<Board> findByIdAndIsDeletedFalseAndIsPublicTrue(Long boardId);
}
