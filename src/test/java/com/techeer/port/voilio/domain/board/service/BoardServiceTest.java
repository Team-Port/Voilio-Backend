package com.techeer.port.voilio.domain.board.service;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.dto.request.BoardUpdateRequest;
import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.dto.response.UpdateFileResponse;
import com.techeer.port.voilio.domain.board.dto.response.UploadFileResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.s3.util.S3Manager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minidev.asm.ex.ConvertException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@DisplayName("Board Service")
public class BoardServiceTest {
  @Mock private BoardRepository boardRepository;
  @Mock private UserRepository userRepository;

  @Mock private S3Manager s3Manager;
  @Spy private BoardMapper boardMapper;

  @InjectMocks private BoardService boardService;

  private User user1;
  private Board board1, board2;

  @BeforeEach
  public void setUp() {
    user1 =
        User.builder()
            .email("tester1@example.com")
            .password("testPassword")
            .nickname("tester1")
            .build();

    board1 =
        Board.builder()
            .user(user1)
            .title("testTitle")
            .content("testContent")
            .category1(Category.IT)
            .category2(Category.IT)
            .video_url("https://www.naver.com/")
            .thumbnail_url("https://www.naver.com")
            .isPublic(true)
            .build();

    board2 =
        Board.builder()
            .user(user1)
            .title("testTitle2")
            .content("testContent2")
            .category1(Category.IT)
            .category2(Category.IT)
            .video_url("https://www.naver.com/")
            .thumbnail_url("https://www.naver.com")
            .isPublic(false)
            .build();
  }

  @Nested
  class softDeleteBoard {
    @Test
    @DisplayName("게시물 삭제 테스트 - 게시물이 존재할 때")
    void softDeleteBoard_whenBoardExists_shouldDeleteBoard() {
      // given
      Long boardId = board1.getId();
      given(boardRepository.findById(board1.getId())).willReturn(Optional.of(board1));

      // when
      boardService.deleteBoard(boardId);

      // then
      verify(boardRepository, times(1)).save(board1);
      verify(boardRepository, times(1)).findById(boardId);
    }

    @Test
    @DisplayName("게시물 삭제 테스트 - 게시물이 존재하지 않을 때")
    public void deleteBoard_whenBoardNotFound_shouldThrowNotFoundException() {
      // given
      Long boardId = board1.getId();
      given(boardRepository.findById(boardId)).willReturn(Optional.empty());

      // when, then
      assertThrows(NotFoundBoard.class, () -> boardService.deleteBoard(boardId));
    }
  }

  @Nested
  class findBoardByKeyword {
    @Test
    @DisplayName("키워드로 게시물 검색 - 게시물이 존재할 떄")
    public void findBoardByKeyword_whenExistBoard() {
      // given
      String keyword = "test";

      List<Board> boards = new ArrayList<>();
      boards.add(board1);
      boards.add(board2);

      given(boardRepository.findBoardByKeyword(keyword)).willReturn(boards);

      // when
      List<BoardResponse> foundBoards = boardService.findBoardByKeyword(keyword);

      // then
      assertEquals(boards.size(), foundBoards.size());
      for (int i = 0; i < boards.size(); i++) {
        assertEquals(boards.get(i).getTitle(), foundBoards.get(i).getTitle());
      }
      verify(boardRepository, times(1)).findBoardByKeyword(keyword);
    }

    @Test
    @DisplayName("키워드로 게시물 검색 - 게시물이 존재때지 않을 때")
    public void findBoardByKeyword_whenNotExistBoard() {
      // given
      String keyword = "no";
      given(boardRepository.findBoardByKeyword(keyword)).willReturn(new ArrayList<>());

      // when, then
      assertThrows(NotFoundBoard.class, () -> boardService.findBoardByKeyword(keyword));
      verify(boardRepository, times(1)).findBoardByKeyword(keyword);
    }
  }

  @Nested
  class findBoardById {
    @Test
    @DisplayName("ID로 게시물 검색 - 게시물이 존재할 때")
    public void findBoardById_whenBoardExists_shouldReturnBoard() {
      Long boardId = board1.getId();
      // given
      given(boardRepository.findBoardById(boardId)).willReturn(Optional.of(board1));

      // when
      BoardResponse actual = boardService.findBoardById(boardId);

      // then
      assertEquals(boardId, actual.getId());
      verify(boardRepository).findBoardById(boardId);
    }

    @Test
    @DisplayName("ID로 게시물 검색 - 게시물이 존재하지 않을 때")
    public void findBoardById_whenBoardDoesNotExist_shouldThrowException() {
      // given
      Long boardId = board1.getId();
      given(boardRepository.findBoardById(boardId)).willReturn(Optional.empty());

      // when, then
      assertThrows(NotFoundBoard.class, () -> boardService.findBoardById(boardId));
      verify(boardRepository).findBoardById(boardId);
    }

    @Test
    @DisplayName("ID로 게시물 검색 - 게시물이 존재하지만 public = false일 때")
    public void testFindBoardByIdExceptHide() {
      Long boardId = board2.getId();
      // given
      given(boardRepository.findBoardByIdExceptHide(boardId)).willReturn(Optional.of(board2));

      // when
      BoardResponse actual = boardService.findBoardByIdExceptHide(boardId);

      // then
      assertEquals(boardId, actual.getId());
      verify(boardRepository).findBoardByIdExceptHide(boardId);
    }
  }

  @Nested
  class createBoard {
    @Test
    @DisplayName("게시물 생성")
    public void testCreateBoard() {
      BoardCreateRequest boardCreateRequest =
          BoardCreateRequest.builder()
              .user_id(user1.getId())
              .title("Test")
              .content("Test")
              .category1(Category.IT)
              .category2(Category.JAVA)
              .video_url("https://www.naver.com/video.mp4")
              .thumbnail_url("https://www.naver.com/thumbnail.jpg")
              .build();

      when(userRepository.findById(boardCreateRequest.getUser_id())).thenReturn(Optional.of(user1));

      assertDoesNotThrow(() -> boardService.createBoard(boardCreateRequest));

      verify(userRepository).findById(boardCreateRequest.getUser_id());
      verify(boardRepository).save(any(Board.class));
    }

    @Test
    @DisplayName("게시물 생성 - 잘못된 파라미터")
    public void testCreatedBoard_InvalidRequest_ThrowException() {
      BoardCreateRequest boardCreateRequest =
          BoardCreateRequest.builder()
              .user_id(123L)
              .title("Test")
              .content("Test")
              .category1(Category.IT)
              .category2(Category.JAVA)
              .video_url("https://www.naver.com/video.mp4")
              .thumbnail_url("https://www.naver.com/thumbnail.jpg")
              .build();

      assertThrows(NotFoundUser.class, () -> boardService.createBoard(boardCreateRequest));

      verify(userRepository).findById(boardCreateRequest.getUser_id());
      verify(boardRepository, never()).save(any(Board.class));
    }
  }

  @Nested
  class hideBoard {
    @Test
    @DisplayName("게시물 숨기기 - 게시물이 존재할 때")
    public void hideBoard_whenBoardExists() {
      // given
      Long boardId = board1.getId();
      given(boardRepository.findById(boardId)).willReturn(Optional.of(board1));

      // when
      boardService.hideBoard(boardId);

      // then
      assertFalse(board1.getIsPublic());
      verify(boardRepository).findById(boardId);
      verify(boardRepository).save(board1);
    }

    @Test
    @DisplayName("게시물 숨기기 - 게시물이 존재하지 않을 때")
    public void hideBoard_whenBoardDoesNotExist() {
      // given
      Long boardId = 3L;
      given(boardRepository.findById(boardId)).willReturn(Optional.empty());

      // when, then
      assertThrows(NotFoundBoard.class, () -> boardService.hideBoard(boardId));
      verify(boardRepository).findById(boardId);
    }
  }

  @Nested
  class findAllBoard {
    @Test
    @DisplayName("모든 게시물 조회 - 게시물이 존재할 때")
    public void findAllBoard_whenBoardExists() {
      // given
      Pageable pageable = mock(Pageable.class);
      List<Board> boards = List.of(board1, board2);
      Page<Board> boardPage = mock(Page.class);

      given(boardPage.getContent()).willReturn(boards);
      given(boardRepository.findAllBoard(pageable)).willReturn(boardPage);

      // when
      Page<Board> result = boardService.findAllBoard(pageable);

      // then
      assertFalse(result.isEmpty());
      assertEquals(boards, result.getContent());
      verify(boardRepository).findAllBoard(pageable);
    }

    @Test
    @DisplayName("모든 게시물 조회 - 게시물이 존재하지 않을 때")
    public void findAllBoard_whenBoardDoesNotExist() {
      // given
      Pageable pageable = mock(Pageable.class);
      Page<Board> boardPage = mock(Page.class);

      given(boardPage.isEmpty()).willReturn(true);
      given(boardRepository.findAllBoard(pageable)).willReturn(boardPage);

      // when, then
      assertThrows(NotFoundBoard.class, () -> boardService.findAllBoard(pageable));
      verify(boardRepository).findAllBoard(pageable);
    }
  }

  @Nested
  class updateBoard {
    @Test
    @DisplayName("게시물 수정 - 게시물이 존재할 때")
    public void updateBoard_whenBoardExists() {
      // given
      Long boardId = board1.getId();
      BoardUpdateRequest request =
          BoardUpdateRequest.builder()
              .title("updatedTitle")
              .content("updatedContent")
              .category1(Category.KOTLIN)
              .category2(Category.PYTHON)
              .thumbnail_url("https://www.naver.com.update/thumbnail.jpg")
              .build();

      given(boardRepository.findById(boardId)).willReturn(Optional.of(board1));
      given(boardRepository.save(any(Board.class))).willReturn(board1);

      // when
      Board updatedBoard = boardService.updateBoard(boardId, request);

      // then
      assertEquals(request.getTitle(), updatedBoard.getTitle());
      assertEquals(request.getContent(), updatedBoard.getContent());
      assertEquals(request.getCategory1(), updatedBoard.getCategory1());
      assertEquals(request.getCategory2(), updatedBoard.getCategory2());
      assertEquals(request.getThumbnail_url(), updatedBoard.getThumbnail_url());

      verify(boardRepository).findById(boardId);
      verify(boardRepository).save(any(Board.class));
    }

    @Test
    @DisplayName("게시물 수정 - 게시물이 존재하지 않을 때")
    public void updateBoard_whenBoardDoesNotExist() {
      // given
      Long boardId = 4L;
      BoardUpdateRequest request =
          BoardUpdateRequest.builder()
              .title("updatedTitle")
              .content("updatedContent")
              .category1(Category.KOTLIN)
              .category2(Category.PYTHON)
              .thumbnail_url("https://www.naver.com.update/thumbnail.jpg")
              .build();

      given(boardRepository.findById(boardId)).willReturn(Optional.empty());

      // when, then
      assertThrows(NotFoundBoard.class, () -> boardService.updateBoard(boardId, request));
      verify(boardRepository).findById(boardId);
      verifyNoMoreInteractions(boardRepository);
    }
  }

  @Nested
  class findBoardByCategory {
    @Test
    @DisplayName("카테고리별 게시물 조회 - 게시물이 존재할 때")
    public void findBoardByCategory_whenBoardExists() {
      // given
      Category category = Category.IT;
      Pageable pageable = mock(Pageable.class);

      List<Board> boardList = new ArrayList<>();
      boardList.add(board1);
      boardList.add(board2);
      Page<Board> boardPage = new PageImpl<>(boardList);

      given(boardRepository.findBoardByCategory(category, category, pageable))
          .willReturn(boardPage);

      // when
      Page<Board> result = boardService.findBoardByCategory(category, pageable);

      // then
      assertFalse(result.isEmpty());
      assertEquals(2, result.getContent().size());

      verify(boardRepository).findBoardByCategory(category, category, pageable);
    }

    @Test
    @DisplayName("카테고리별 게시물 조회 - 게시물이 존재하지 않을 때")
    public void findBoardByCategory_whenBoardDoesNotExist() {
      // given
      Category category = Category.KOTLIN;
      Pageable pageable = mock(Pageable.class);

      Page<Board> emptyBoardPage = new PageImpl<>(new ArrayList<>());

      given(boardRepository.findBoardByCategory(category, category, pageable))
          .willReturn(emptyBoardPage);

      // when, then
      assertThrows(NotFoundBoard.class, () -> boardService.findBoardByCategory(category, pageable));
      verify(boardRepository).findBoardByCategory(category, category, pageable);
    }
  }

  @Nested
  class findBoardByUserNickname {
    @Test
    @DisplayName("유저 닉네임으로 게시물 조회 - 게시물이 존재할 때")
    public void findBoardByUserNickname_whenBoardExist() {
      // given
      String nickname = user1.getNickname();
      Pageable pageable = mock(Pageable.class);

      List<Board> boardList = new ArrayList<>();
      boardList.add(board1);
      Page<Board> boardPage = new PageImpl<>(boardList);

      given(userRepository.findUserByNickname(nickname)).willReturn(Optional.of(user1));
      given(boardRepository.findBoardByUserNickname(nickname, pageable)).willReturn(boardPage);

      // when
      Page<Board> result = boardService.findBoardByUserNickname(nickname, pageable);

      // then
      assertFalse(result.isEmpty());
      assertEquals(1, result.getContent().size());

      verify(userRepository).findUserByNickname(nickname);
      verify(boardRepository).findBoardByUserNickname(nickname, pageable);
    }

    @Test
    @DisplayName("유저 닉네임으로 게시물 조회 - 게시물이 존재하지 않을 때")
    public void findBoardByUserNickname_whenBoardDoesNotExist() {
      // given
      String nickname = user1.getNickname();
      Pageable pageable = mock(Pageable.class);

      given(userRepository.findUserByNickname(nickname)).willReturn(Optional.of(user1));
      given(boardRepository.findBoardByUserNickname(nickname, pageable)).willReturn(Page.empty());

      // when, then
      assertThrows(
          NotFoundBoard.class, () -> boardService.findBoardByUserNickname(nickname, pageable));

      verify(userRepository).findUserByNickname(nickname);
      verify(boardRepository).findBoardByUserNickname(nickname, pageable);
    }

    @Test
    @DisplayName("유저 닉네임으로 게시물 조회 - 유저가 존재하지 않을 때")
    public void findBoardByUserNickname_whenUserNotFound() {
      // given
      String nickname = "nonexistentNickname";
      Pageable pageable = mock(Pageable.class);

      given(userRepository.findUserByNickname(nickname)).willReturn(null);
      given(boardRepository.findBoardByUserNickname(nickname, pageable)).willReturn(Page.empty());

      // when, then
      assertThrows(
          NotFoundUser.class, () -> boardService.findBoardByUserNickname(nickname, pageable));

      verify(userRepository).findUserByNickname(nickname);
      verify(boardRepository).findBoardByUserNickname(nickname, pageable);
    }

    @Test
    @DisplayName("유저 닉네임으로 숨김게시물까지 조회 - 게시물이 존재할 때")
    public void findBoardByUserNicknameExceptHide_whenBoardExists() {
      // given
      String nickname = user1.getNickname();
      Pageable pageable = mock(Pageable.class);

      List<Board> boardList = new ArrayList<>();
      boardList.add(board2);
      Page<Board> boardPage = new PageImpl<>(boardList);

      given(userRepository.findUserByNickname(nickname)).willReturn(Optional.of(user1));
      given(boardRepository.findBoardByUserNicknameExceptHide(nickname, pageable))
          .willReturn(boardPage);

      // when
      Page<Board> result = boardService.findBoardByUserNicknameExceptHide(nickname, pageable);

      // then
      assertFalse(result.isEmpty());
      assertEquals(1, result.getContent().size());

      verify(userRepository).findUserByNickname(nickname);
      verify(boardRepository).findBoardByUserNicknameExceptHide(nickname, pageable);
    }
  }

  @Nested
  class uploadFiles {
    @Test
    @DisplayName("파일 업로드")
    public void uploadFiles() throws IOException {
      // given
      MultipartFile videoFile = mock(MultipartFile.class);
      MultipartFile thumbnailFile = mock(MultipartFile.class);
      String video_url = "https://www.naver.com/video.mp4";
      String thumbnail_url = "https://www.naver.com/thumbnail.jpeg";
      UploadFileResponse uploadFileResponse = new UploadFileResponse(video_url, thumbnail_url);

      given(s3Manager.upload(videoFile, "video")).willReturn(video_url);
      given(s3Manager.upload(thumbnailFile, "thumbnail")).willReturn(thumbnail_url);

      // when
      UploadFileResponse result = boardService.uploadFiles(videoFile, thumbnailFile);

      // then
      assertEquals(uploadFileResponse.getVideo_url(), result.getVideo_url());
      assertEquals(uploadFileResponse.getThumbnail_url(), result.getThumbnail_url());

      verify(s3Manager).upload(videoFile, "video");
      verify(s3Manager).upload(thumbnailFile, "thumbnail");
    }

    @Test
    @DisplayName("파일 업로드 - IOException 오류가 뜰 때")
    public void uploadFiles_whenIOExceptionThrown() throws IOException {
      // given
      MultipartFile videoFile = mock(MultipartFile.class);
      MultipartFile thumbnailFile = mock(MultipartFile.class);

      given(s3Manager.upload(videoFile, "video")).willThrow(new IOException());

      // when, then
      assertThrows(
          ConvertException.class, () -> boardService.uploadFiles(videoFile, thumbnailFile));

      verify(s3Manager).upload(videoFile, "video");
    }
  }

  @Nested
  class updateFiles {
    @Test
    @DisplayName("파일 업데이트")
    public void updateFiles() throws IOException {
      // given
      MultipartFile thumbnailFile = mock(MultipartFile.class);
      String thumbnail_url = "https://www.naver.com/thumbnail.jpeg";
      UpdateFileResponse updateFileResponse = new UpdateFileResponse(thumbnail_url);

      given(s3Manager.upload(thumbnailFile, "thumbnail")).willReturn(thumbnail_url);

      // when
      UploadFileResponse result = boardService.updateFiles(thumbnailFile);

      // then
      assertEquals(updateFileResponse.getThumbnail_url(), result.getThumbnail_url());

      verify(s3Manager).upload(thumbnailFile, "thumbnail");
    }

    @Test
    @DisplayName("파일 업데이트 - IOException 오류가 뜰 때")
    public void updateFiles_whenIOExceptionThrown() throws IOException {
      // given
      MultipartFile thumbnailFile = mock(MultipartFile.class);

      given(s3Manager.upload(thumbnailFile, "thumbnail")).willThrow(new IOException());

      // when, then
      assertThrows(ConvertException.class, () -> boardService.updateFiles(thumbnailFile));

      verify(s3Manager).upload(thumbnailFile, "thumbnail");
    }
  }
}
