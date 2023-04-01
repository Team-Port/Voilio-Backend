package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
  Optional<Board> findByIdAndIsDeletedFalseAndIsPublicTrue(Long boardId);

  Page<Board> findAllByIsDeletedAndIsPublicOrderByCreateAtDesc(
      Boolean isDeleted, Boolean isPublic, Pageable pageable);
}
