package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.DateType;
import com.techeer.port.voilio.global.common.YnType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {

  Page<Board> findBoardByKeyword(String keyword, YnType delYn, YnType isPublic, Pageable pageable);

  Page<Board> findBoardByCategoryAndKeyword(
      Category category,
      DateType dateType,
      String keyword,
      YnType delYn,
      YnType isPublic,
      Pageable pageable);
}
