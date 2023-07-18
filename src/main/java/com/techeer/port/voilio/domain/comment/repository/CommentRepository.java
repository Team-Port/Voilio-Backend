package com.techeer.port.voilio.domain.comment.repository;

import com.techeer.port.voilio.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {}
