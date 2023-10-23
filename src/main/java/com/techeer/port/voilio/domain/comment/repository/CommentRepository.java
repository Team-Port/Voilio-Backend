package com.techeer.port.voilio.domain.comment.repository;

import com.techeer.port.voilio.domain.comment.entity.Comment;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("SELECT c FROM Comment c join fetch c.board where c.delYn = 'N' and c.board.id=:id")
  List<Comment> findByBoardId(@Param("id") Long id);

  Optional<Comment> findByBoardIdAndId(Long boardId, Long parentId);
}
