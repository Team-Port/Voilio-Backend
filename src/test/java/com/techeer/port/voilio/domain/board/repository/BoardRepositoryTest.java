package com.techeer.port.voilio.domain.board.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.Category;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BoardRepositoryTest {
  @Autowired private BoardRepository boardRepository;
  @Autowired private UserRepository userRepository;

  private Board board1, board2, board3;
  private User user1, user2;

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

  @Nested
  class deleteBoardTest {
    @Test
    @DisplayName("deleteBoardTest_whenExistBoard")
    public void deleteBoardTest_whenExistBoard() {
      Long boardId = board1.getId();

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
    @DisplayName("deleteBoardTest_whenHidenBoard")
    public void deleteBoardTest_whenHidenBoard() {
      long boardId = board1.getId();
      board1.changePublic();
      boardRepository.save(board1);

      Board board = boardRepository.findById(boardId).orElseThrow();
      board.changeDeleted();
      Board changedBoard = boardRepository.save(board);
      assertTrue(changedBoard.getIsDeleted());
    }
  }

  @Nested
  class findBoardByKeyword {
    @Test
    @DisplayName("findBoardBykeyword")
    public void findBoardByKeyword() {
      String keyword = "test",
          keyword2 = "different",
          keyword3 = "",
          keyword4 = "nothing",
          keyword5 = "differentTitle2";

      // given
      Board tempBoard =
          boardRepository.save(
              Board.builder()
                  .user(user2)
                  .title("differentTitle")
                  .content("differentContent")
                  .category2(Category.IT)
                  .category1(Category.IT)
                  .isPublic(true)
                  .video_url("https://www.naver.com/")
                  .thumbnail_url("https://www.naver.com")
                  .build());

      Board tempBoard2 =
          boardRepository.save(
              Board.builder()
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

      // when
      List<Board> actualBoards =
          boardRepository.findBoardByKeyword(keyword);
      List<Board> actualBoards2 =
          boardRepository.findBoardByKeyword(keyword2);
      List<Board> actualBoards3 =
          boardRepository.findBoardByKeyword(keyword3);
      List<Board> actualBoards4 =
          boardRepository.findBoardByKeyword(keyword4);
      List<Board> actualBoards5 =
          boardRepository.findBoardByKeyword(keyword5);

      // then
      assertEquals(expectBoards.size(), actualBoards.size());
      assertEquals(expectBoards2.size(), actualBoards2.size());
      assertEquals(4, actualBoards3.size());
      assertEquals(expectBoards4, actualBoards4);
      assertEquals(expectBoards5, actualBoards5);
    }
  }

  @Nested
  class testFindBoardByBoardId {
    @Test
    @DisplayName("testFindBoardByBoardId_when_existedBoard")
    public void testFindBoardByBoardId() {
      // given
      Board existedBoard = board1;
      Board existedBoard2 = board2;

      // when
      Board foundBoard1 =
          boardRepository
              .findById(existedBoard.getId())
              .orElseThrow();

      // then
      assertEquals(foundBoard1.getId(), existedBoard.getId());
      assertTrue(foundBoard1.getIsPublic());
      assertFalse(foundBoard1.getIsDeleted());
    }

    @Test
    @DisplayName("testFindBoardByBoardId_when_not_exitedBoard")
    public void testFindBoardByBoardId_when_not_exitedBoard() {
      // given
      long boardId = board3.getId();

      // when, then
      Throwable exception =
          assertThrows(
              NotFoundBoard.class,
              () -> {
                boardRepository
                    .findById(boardId + 1)
                    .orElseThrow(NotFoundBoard::new);
              });

      assertEquals("게시글을 찾을 수 없음", exception.getMessage());
    }

    @Test
    @DisplayName("testFindBoardByBoardId_when_hidenBoard")
    public void testFindBoardByBoardId_when_hidenBoard() {
      // given
      board1.changePublic();
      boardRepository.save(board1);

      // when,then
      Throwable exception =
          assertThrows(
              NotFoundBoard.class,
              () -> {
                boardRepository
                    .findById(board1.getId())
                    .orElseThrow(NotFoundBoard::new);
              });

      assertEquals("게시글을 찾을 수 없음", exception.getMessage());
    }

    @Test
    @DisplayName("testFindBoardByBoardId_when_deletedBoard")
    public void testFindBoardByBoardId_when_deletedBoard() {
      // given
      board1.changeDeleted();
      boardRepository.save(board1);

      // when, then
      Throwable exception =
          assertThrows(
              NotFoundBoard.class,
              () -> {
                boardRepository
                    .findById(board1.getId())
                    .orElseThrow(NotFoundBoard::new);
              });

      assertEquals("게시글을 찾을 수 없음", exception.getMessage());
    }
  }

  @Nested
  class hideBoardTest {
    @Test
    public void testHideBoard_whenExistBoard() {
      // given
      board1.changePublic();
      boardRepository.save(board1);

      // when,then
    }
  }
  @Test
  @DisplayName("testFindAllBoard")
  public void testFindAllBoard() {
    List<Board> expectedBoards = new ArrayList<>();
    expectedBoards.add(board1);
    expectedBoards.add(board2);
    expectedBoards.add(board3);

    Pageable pageable = PageRequest.of(0, 10);
    Page<Board> actualPage = boardRepository.findAllBoard(pageable);
    List<Board> actualBoards = actualPage.getContent();

    assertEquals(expectedBoards.size(), actualBoards.size());
    for(int i=0; i<expectedBoards.size(); i++) {
      assertEquals(expectedBoards.get(i).getId(), actualBoards.get(i).getId());
      assertEquals(expectedBoards.get(i).getIsPublic(), actualBoards.get(i).getIsPublic());
      assertEquals(expectedBoards.get(i).getContent(), actualBoards.get(i).getContent());
      assertEquals(expectedBoards.get(i).getVideo_url(), actualBoards.get(i).getVideo_url());
      assertEquals(expectedBoards.get(i).getThumbnail_url(), actualBoards.get(i).getThumbnail_url());
      assertEquals(expectedBoards.get(i).getCategory1(), actualBoards.get(i).getCategory1());
      assertEquals(expectedBoards.get(i).getCategory2(), actualBoards.get(i).getCategory2());
    }
  }
}
