package com.techeer.port.voilio.domain.comment.repository;

import com.techeer.port.voilio.domain.comment.entity.Comment;

import java.util.List;

public interface CommentCustomRepository {

    List<Comment> findByBoardId(Long id);
}
