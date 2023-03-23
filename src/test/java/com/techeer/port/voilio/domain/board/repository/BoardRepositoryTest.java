package com.techeer.port.voilio.domain.board.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.global.common.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BoardRepositoryTest {
  @Autowired private BoardRepository boardRepository;

  private Board board1;
  private Board board2;

  @BeforeEach
  public void setUp() {
    board1 =
        Board.builder()
            .userId(1L)
            .title("testTitle1")
            .content("testContent1")
            .isPublic(true)
            .category1(Category.IT)
            .video("https://www.naver.com/")
            .thumbnail("https://www.naver.com")
            .build();
    board2 =
        Board.builder()
            .userId(1L)
            .title("testTitle2")
            .category1(Category.IT)
            .content("testContent2")
            .isPublic(false)
            .video("https://www.naver.com/")
            .thumbnail("https://www.naver.com")
            .build();

    board1 = boardRepository.save(board1);
    board2 = boardRepository.save(board2);
  }
  //
  //    @Test
  //    @DisplayName("deleteBoardTest Start")
  //    public void deleteBoardTest(){
  //        //given
  //        List<Board> boardList = boardRepository.findAll();
  //        int boardSize = boardList.size();
  //        Board board = boardRepository.findById(1).orElseThrow();
  //        assertFalse(board.getIsDeleted());
  //
  //        //when
  //        boardRepository.deleteById(1);
  //
  //        //then
  //        boardList = boardRepository.findAll();
  //        assertEquals(boardList.size(),boardSize);
  //        board = boardRepository.findById(1).orElseThrow();
  //        assertTrue(board.getIsDeleted());
  //
  //    }

  @Test
  @DisplayName("testFindBoardByBoardId_when_existedBoard")
  public void testFindBoardByBoardId() {
    // given
    Board existedBoard = board1;
    Board existedBoard2 = board2;

    // when
    Board foundBoard1 =
        boardRepository
            .findByIdAndIsDeletedFalseAndIsPublicTrue(existedBoard.getId())
            .orElseThrow();
    //         Board foundBoard2 = boardRepository.fin

    //         System.out.println(foundBoard);

    // then
    assertEquals(foundBoard1.getId(), existedBoard.getId());
    assertTrue(foundBoard1.getIsPublic());
    assertFalse(foundBoard1.getIsDeleted());
  }
}
