package com.techeer.port.voilio.domain.comment.repository;

import com.techeer.port.voilio.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;import org.springframework.data.repository.query.Param;
import java.util.List;import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("SELECT c FROM Comment c join fetch c.board where c.isDeleted = false and c.board.id=:id")
  List<Comment> findByBoardId(@Param("id") Long id);
}
