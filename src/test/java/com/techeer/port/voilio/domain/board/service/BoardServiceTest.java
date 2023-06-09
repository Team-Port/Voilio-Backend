// package com.techeer.port.voilio.domain.board.service;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.BDDMockito.given;
// import static org.mockito.Mockito.*;
//
// import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
// import com.techeer.port.voilio.domain.board.entity.Board;
// import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
// import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
// import com.techeer.port.voilio.domain.board.repository.BoardRepository;
// import com.techeer.port.voilio.domain.user.entity.User;
// import com.techeer.port.voilio.domain.user.repository.UserRepository;
// import com.techeer.port.voilio.global.common.Category;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
// import org.junit.jupiter.api.*;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Spy;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.test.context.ActiveProfiles;
//
// @ExtendWith(MockitoExtension.class)
// @ActiveProfiles("test")
// @DisplayName("Board Service")
// public class BoardServiceTest {
//  @Mock private BoardRepository boardRepository;
//  @Mock private UserRepository userRepository;
//  @Spy private BoardMapper boardMapper;
//
//  @InjectMocks private BoardService boardService;
//
//  private User user1;
//  private Board board1, board2;
//
//  @BeforeEach
//  public void setUp() {
//    user1 =
//        User.builder()
//            .email("tester1@example.com")
//            .password("testPassword")
//            .nickname("tester1")
//            .build();
//
//    board1 =
//        Board.builder()
//            .user(user1)
//            .title("testTitle")
//            .content("testContent")
//            .category1(Category.IT)
//            .category2(Category.IT)
//            .video_url("https://www.naver.com/")
//            .thumbnail_url("https://www.naver.com")
//            .build();
//
//    board2 =
//        Board.builder()
//            .user(user1)
//            .title("testTitle2")
//            .content("testContent2")
//            .category1(Category.IT)
//            .category2(Category.IT)
//            .video_url("https://www.naver.com/")
//            .thumbnail_url("https://www.naver.com")
//            .build();
//  }
//
//  @Nested
//  class softDeleteBoard {
//    @Test
//    @DisplayName("mockito service test")
//    void softDeleteBoard_whenBoardExists_shouldDeleteBoard() {
//      // given
//      Long boardId = board1.getId();
//      given(boardRepository.findById(board1.getId())).willReturn(Optional.of(board1));
//
//      // when
//      boardService.deleteBoard(boardId);
//
//      // then
//      verify(boardRepository, times(1)).save(board1);
//      verify(boardRepository, times(1)).findById(boardId);
//    }
//
//    @Test
//    @DisplayName("deleteBoard_whenBoardNotFound_shouldThrowNotFoundException")
//    public void deleteBoard_whenBoardNotFound_shouldThrowNotFoundException() {
//      // given
//      Long boardId = board1.getId();
//      given(boardRepository.findById(boardId)).willReturn(Optional.empty());
//
//      // when, then
//      assertThrows(NotFoundBoard.class, () -> boardService.deleteBoard(boardId));
//    }
//  }
//
//  @Nested
//  class findBoardByKeyword {
//    @Test
//    @DisplayName("findBoardByKeyword_whenExistBoard")
//    public void findBoardByKeyword_whenExistBoard() {
//      // given
//      String keyword = "test";
//
//      List<Board> boards = new ArrayList<>();
//      boards.add(board1);
//      boards.add(board2);
//
//      given(boardRepository.findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword))
//          .willReturn(boards);
//
//      // when
//      List<BoardResponse> foundBoards = boardService.findBoardByKeyword(keyword);
//
//      // then
//      assertEquals(boards.size(), foundBoards.size());
//      for (int i = 0; i < boards.size(); i++) {
//        assertEquals(boards.get(i).getTitle(), foundBoards.get(i).getTitle());
//      }
//      verify(boardRepository, times(1))
//          .findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword);
//    }
//
//    @Test
//    @DisplayName("findBoardByKeyword_whenNotExistBoard")
//    public void findBoardByKeyword_whenNotExistBoard() {
//      // given
//      String keyword = "no";
//      given(boardRepository.findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword))
//          .willReturn(new ArrayList<>());
//
//      // when
//      List<BoardResponse> foundBoards = boardService.findBoardByKeyword(keyword);
//
//      // then
//      verify(boardRepository, times(1))
//          .findAllByTitleContainingAndIsPublicTrueAndIsDeletedFalse(keyword);
//    }
//  }
//
//  @Nested
//  class getBoardById {
//
//    @Test
//    @DisplayName("getBoard_when success")
//    public void getBoard_whenBoardExist_shouldGetBoard() {
//      Long boardId = board1.getId();
//
//      // given
//      given(boardRepository.findByIdAndIsDeletedFalseAndIsPublicTrue(boardId))
//          .willReturn(Optional.of(board1));
//
//      // when
//      BoardResponse actual = boardService.findBoardById(boardId);
//
//      assertEquals(boardId, actual.getId());
//    }
//
//    @Test
//    @DisplayName("getBoard_whenHidenBoard_souldNotFoundBoard")
//    public void getBoard_whenHidenBoard_souldNotFoundBoard() {
//      Long boardId = board1.getId();
//      // given
//      given(boardRepository.findByIdAndIsDeletedFalseAndIsPublicTrue(any()))
//          .willThrow(NotFoundBoard.class);
//
//      // when,then
//      assertThrows(
//          NotFoundBoard.class,
//          () -> {
//            boardService.findBoardById(boardId);
//          });
//    }
//  }
// }
