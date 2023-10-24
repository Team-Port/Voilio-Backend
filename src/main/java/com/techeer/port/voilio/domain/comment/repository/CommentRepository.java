package com.techeer.port.voilio.domain.comment.repository;

import com.techeer.port.voilio.domain.comment.entity.Comment;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.YnType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findAllByBoardIdAndDelYnAndParentCommentIsNull(Long boardId, YnType delYn);

  Optional<Comment> findByBoardIdAndId(Long boardId, Long parentId);

  Optional<Comment> findByIdAndUser(Long id, User user);
}
