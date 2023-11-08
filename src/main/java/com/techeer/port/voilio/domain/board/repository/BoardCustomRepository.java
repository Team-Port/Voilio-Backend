package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.YnType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface BoardCustomRepository {

  Page<Board> findBoardByKeyword(String keyword, YnType delYn, YnType isPublic, Pageable pageable);
}
