package com.techeer.port.voilio.domain.board.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@DisplayName("Board Service")
public class BoardServiceTest {
  @Mock private BoardRepository boardRepository;

  @InjectMocks private BoardService boardService;

  @Test
  @DisplayName("mockito service test")
  void softDeleteBoard_whenBoardExists_shouldDeleteBoard() {
    Long boardId = 1L;

    // given
    Board board1 =
        Board.builder()
            .userId(1L)
            .title("testTitle")
            .content("testContent")
            .video("https://www.naver.com/")
            .thumbnail("https://www.naver.com")
            .build();
    given(boardRepository.findById(boardId)).willReturn(Optional.of(board1));

    // when
    boardService.deleteBoard(boardId);

    // then
    verify(boardRepository, times(1)).save(board1);
    assertTrue(board1.getIsDeleted());
  }

  @Test
  public void deleteBoard_whenBoardNotFound_shouldThrowNotFoundException() {
    Long boardId = 1L;

    // given
    given(boardRepository.findById(boardId)).willReturn(Optional.empty());

    // when, then
    assertThrows(NotFoundException.class, () -> boardService.deleteBoard(boardId));
  }

  @Test
  @DisplayName("getBoard_when success")
  public void getBoard_whenBoardExist_shouldGetBoard(){
    Long boardId = 1L;

    //given
    Board board =Board.builder()
            .id(boardId)
            .userId(1L)
            .category1(Category.IT)
            .thumbnail("https://www.youtube.com/watch?v=PMV-q3eX4hk")
            .content("testContent")
            .video("https://www.youtube.com/watch?v=PMV-q3eX4hk")
            .title("testTitle")
            .isPublic(true)
            .build();

    given(boardRepository.findByIdAndIsDeletedFalseAndIsPublicTrue(boardId)).willReturn(Optional.of(board));

    //when
    Board actual = boardService.findBoardById(board.getId());

    assertEquals(actual.getId(),boardId);
    assertEquals(actual.getUserId(),board.getUserId());
    assertTrue(actual.getIsPublic());
    assertFalse(actual.getIsDeleted());


  }
}
