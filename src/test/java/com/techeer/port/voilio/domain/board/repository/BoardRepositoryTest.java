package com.techeer.port.voilio.domain.board.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.internal.matchers.text.ValuePrinter.print;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.Category;

import java.util.ArrayList;
import java.util.List;
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
  @Autowired private UserRepository userRepository;

  private Board board1,board2,board3;
  private User user1,user2;


  @BeforeEach
  public void saveTest() {
    user1 =
        userRepository.save(
            User.builder()
                .email("tester1@example.com")
                .password("testPassword")
                .nickname("tester1")
                .build());

    user2 =
        userRepository.save(
            User.builder()
                .email("tester2@example.com")
                .password("testPassword")
                .nickname("tester2")
                .build());

    board1 =
        boardRepository.save(
            Board.builder()
                .user(user1)
                .title("testTitle")
                .content("testContent")
                .category1(Category.IT)
                .category2(Category.IT)
                .isPublic(true)
                .video_url("https://www.naver.com/")
                .thumbnail_url("https://www.naver.com")
                .build());

    board2 =
        boardRepository.save(
            Board.builder()
                .user(user1)
                .title("testTitle2")
                .content("testContent2")
                .category1(Category.IT)
                .category2(Category.IT)
                .isPublic(true)
                .video_url("https://www.naver.com/")
                .thumbnail_url("https://www.naver.com")
                .build());

    board3 =
        boardRepository.save(
            Board.builder()
                .user(user1)
                .title("testTitle3")
                .content("testContent3")
                .category2(Category.IT)
                .category1(Category.IT)
                .isPublic(true)
                .video_url("https://www.naver.com/")
                .thumbnail_url("https://www.naver.com")
                .build());
  }

  @Test
  @DisplayName("deleteBoardTest Start")
  public void deleteBoardTest() {
    Long boardId = 1L;

    // given
    List<Board> boardList = boardRepository.findAll();
    int boardSize = boardList.size();
    Board board = boardRepository.findById(boardId).orElseThrow();
    assertFalse(board.getIsDeleted());

    // when
    board.changeDeleted();
    boardRepository.save(board);

    // then
    boardList = boardRepository.findAll();
    assertEquals(boardList.size(), boardSize);
    board = boardRepository.findById(boardId).orElseThrow();
    assertTrue(board.getIsDeleted());
  }

  @Test
  @DisplayName("findBoardBykeyword")
  public void findBoardByKeyword() {
    String keyword = "test", keyword2 = "different", keyword3 = "", keyword4 = "nothing", keyword5 = "differentTitle2";

    //given
    Board tempBoard = boardRepository.save(Board.builder()
            .user(user2)
            .title("differentTitle")
            .content("differentContent")
            .category2(Category.IT)
            .category1(Category.IT)
            .isPublic(true)
            .video_url("https://www.naver.com/")
            .thumbnail_url("https://www.naver.com")
            .build());

    Board tempBoard2 = boardRepository.save(Board.builder()
            .user(user2)
            .title("differentTitle2")
            .content("differentContent2")
            .category2(Category.IT)
            .category1(Category.IT)
            .isPublic(false)
            .video_url("https://www.naver.com/")
            .thumbnail_url("https://www.naver.com")
            .build());

    assertEquals(boardRepository.findAll().size(), 5);

    List<Board> expectBoards = new ArrayList<>();
    expectBoards.add(board1);
    expectBoards.add(board2);
    expectBoards.add(board3);

    List<Board> expectBoards2 = new ArrayList<>();
    expectBoards2.add(tempBoard);

    List<Board> expectBoards4 = new ArrayList<>();
    List<Board> expectBoards5 = expectBoards4;


    //when
    List<Board> actualBoards = boardRepository.findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword);
    List<Board> actualBoards2 = boardRepository.findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword2);
    List<Board> actualBoards3 = boardRepository.findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword3);
    List<Board> actualBoards4 = boardRepository.findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword4);
    List<Board> actualBoards5 = boardRepository.findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword5);


    //then
    assertEquals(expectBoards.size(), actualBoards.size());
    assertEquals(expectBoards2.size(), actualBoards2.size());
    assertEquals(4, actualBoards3.size());
    assertEquals(expectBoards4, actualBoards4);
    assertEquals(expectBoards5, actualBoards5);

  }

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

    // then
    assertEquals(foundBoard1.getId(), existedBoard.getId());
    assertTrue(foundBoard1.getIsPublic());
    assertFalse(foundBoard1.getIsDeleted());

  }
}
