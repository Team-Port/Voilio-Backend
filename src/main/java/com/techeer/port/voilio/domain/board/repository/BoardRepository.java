package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByTitleContaining(String keyword);

    List<Board> findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(String keyword);
}
