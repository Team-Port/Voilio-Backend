package com.techeer.port.voilio.domain.comment.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.comment.entity.Comment;
import java.util.List;
import java.util.Optional;

import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.YnType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findAllByBoardIdAndDelYnAndParentCommentIsNull(Long boardId, YnType delYn);

  Optional<Comment> findByBoardIdAndId(Long boardId, Long parentId);

  Optional<Comment> findByIdAndUser(Long id, User user);
}
