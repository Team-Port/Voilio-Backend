//package com.techeer.port.voilio.domain.board.controller;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
//import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
//import com.techeer.port.voilio.domain.board.dto.response.UploadFileResponse;
//import com.techeer.port.voilio.domain.board.entity.Board;
//import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
//import com.techeer.port.voilio.domain.board.service.BoardService;
//import com.techeer.port.voilio.domain.user.entity.User;
//import com.techeer.port.voilio.domain.user.service.UserService;
//import com.techeer.port.voilio.global.common.Category;
//import com.techeer.port.voilio.global.common.Pagination;
//import com.techeer.port.voilio.global.common.YnType;
//import com.techeer.port.voilio.global.result.ResultResponse;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.multipart.MultipartFile;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("BoardController Test Start")
//public class BoardControllerTest {
//
//  @Mock private BoardService boardService;
//  @Mock private UserService userService;
//  @Mock private BoardMapper boardMapper;
//
//  @InjectMocks private BoardController boardController;
//  private MockMvc mockMvc;
//
//  private static String BASE_PATH = "http://localhost/api/v1/boards";
//
//  @BeforeEach
//  public void setUp() {
//    mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
//  }
//
//  @Test
//  public void softDeleteBoard() throws Exception {
//    Long boardId = 1L;
//
//    // given,when
//    doNothing().when(boardService).deleteBoard(boardId);
//
//    // then
//    mockMvc
//        .perform(delete(BASE_PATH + "/{boardId}", boardId))
//        .andDo(print())
//        .andExpect(status().isOk());
//    verify(boardService, times(1)).deleteBoard(boardId);
//  }
//
//  @Test
//  public void findBoardById_authenticatedUser() throws Exception {
//    // given
//    Long boardId = 1L;
//    String authorizationHeader = "Bearer <token>";
//    Long currentLoginUserId = 123L;
//
//    BoardResponse boardResponse =
//        BoardResponse.builder()
//            .id(boardId)
//            .title("Test")
//            .content("Test")
//            .category1(Category.IT)
//            .category2(Category.JAVA)
//            .video_url("https://www.naver.com/video.mp4")
//            .thumbnail_url("https://www.naver.com/thumbnail.jpeg")
//            .user_id(123L)
//            .nickname("tester")
//            .isPublic(YnType.N)
//            .build();
//
//    given(boardService.findBoardByIdExceptHide(boardId)).willReturn(boardResponse);
//    given(userService.getCurrentLoginUser(authorizationHeader)).willReturn(currentLoginUserId);
//    given(userService.getUserIdByBoardId(boardId)).willReturn(currentLoginUserId);
//
//    // when
//    ResponseEntity<EntityModel<ResultResponse<Board>>> responseEntity =
//        boardController.findBoardById(boardId, authorizationHeader);
//
//    // then
//    mockMvc
//        .perform(
//            get(BASE_PATH + "/{board_id}", boardId).header("Authorization", authorizationHeader))
//        .andDo(print())
//        .andExpect(status().isOk());
//  }
//
//  @Test
//  public void findBoardById_unauthenticatedUser() throws Exception {
//    // given
//    Long boardId = 1L;
//    String authorizationHeader = "";
//
//    BoardResponse boardResponse =
//        BoardResponse.builder()
//            .id(boardId)
//            .title("Test")
//            .content("Test")
//            .category1(Category.IT)
//            .category2(Category.JAVA)
//            .video_url("https://www.naver.com/video.mp4")
//            .thumbnail_url("https://www.naver.com/thumbnail.jpeg")
//            .user_id(123L)
//            .nickname("tester")
//            .isPublic(YnType.N)
//            .build();
//
//    given(boardService.findBoardById(boardId)).willReturn(boardResponse);
//    given(userService.getCurrentLoginUser(authorizationHeader)).willReturn(null);
//
//    // when
//    ResponseEntity<EntityModel<ResultResponse<Board>>> responseEntity =
//        boardController.findBoardById(boardId, authorizationHeader);
//
//    // then
//    mockMvc
//        .perform(
//            get(BASE_PATH + "/{board_id}", boardId).header("Authorization", authorizationHeader))
//        .andDo(print())
//        .andExpect(status().isOk());
//  }
//
//  @Test
//  @DisplayName("게시물 생성 테스트")
//  public void testCreateBoard() throws Exception {
//    // Mock 데이터 설정
//    Long userId = 1L;
//    String title = "Test Title";
//    String content = "Test Content";
//    String videoUrl = "http://example.com/video.mp4";
//    String thumbnailUrl = "http://example.com/thumbnail.jpg";
//
//    MultipartFile videoFile =
//        new MockMultipartFile(
//            "video", "video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "test video".getBytes());
//    MultipartFile thumbnailFile =
//        new MockMultipartFile(
//            "thumbnail",
//            "thumbnail.jpg",
//            MediaType.MULTIPART_FORM_DATA_VALUE,
//            "test thumbnail".getBytes());
//
//    UploadFileResponse uploadFileResponse =
//        UploadFileResponse.builder().video_url(videoUrl).thumbnail_url(thumbnailUrl).build();
//
//    when(boardService.uploadFiles(any(MultipartFile.class), any(MultipartFile.class)))
//        .thenReturn(uploadFileResponse);
//
//    doAnswer(
//            invocation -> {
//              BoardCreateRequest argument = invocation.getArgument(0);
//              return null;
//            })
//        .when(boardService)
//        .createBoard(any(BoardCreateRequest.class));
//
//    // MockMvc를 사용하여 API 테스트
//    mockMvc
//        .perform(
//            MockMvcRequestBuilders.multipart(BASE_PATH + "/create")
//                .file("video", videoFile.getBytes())
//                .file("thumbnail", thumbnailFile.getBytes())
//                .param("user_id", String.valueOf(userId))
//                .param("title", title)
//                .param("content", content)
//                .param("category1", Category.IT.toString())
//                .param("category2", Category.JAVA.toString()))
//        .andExpect(MockMvcResultMatchers.status().isOk())
//        .andDo(print());
//
//    // 특정 메서드가 특정 인자로 호출되었는지 검증
//    verify(boardService, times(1)).uploadFiles(any(MultipartFile.class), any(MultipartFile.class));
//    verify(boardService, times(1)).createBoard(any(BoardCreateRequest.class));
//  }
//
//  @Test
//  public void findAllBoard() throws Exception {
//    // given
//    String authorizationHeader = "Bearer <token>";
//
//    User user =
//        User.builder()
//            .email("tester1@example.com")
//            .password("testPassword")
//            .nickname("tester1")
//            .build();
//    Board board =
//        Board.builder()
//            .user(user)
//            .title("Test Board")
//            .content("Test Content")
//            .category1(Category.IT)
//            .category2(Category.KOTLIN)
//            .videoUrl("http://example.com/video")
//            .thumbnail_url("http://example.com/thumbnail")
//            .isPublic(YnType.Y)
//            .build();
//
//    List<Board> boardList = List.of(board);
//    Page<Board> boardPage = new PageImpl<>(boardList);
//
//    given(boardService.findAllBoard(any())).willReturn(boardPage);
//    given(boardMapper.toDto(any(Board.class)))
//        .willAnswer(
//            invocation -> {
//              Board boardArg = invocation.getArgument(0);
//              return BoardResponse.builder()
//                  .id(boardArg.getId())
//                  .title(boardArg.getTitle())
//                  .content(boardArg.getContent())
//                  .category1(boardArg.getCategory1())
//                  .category2(boardArg.getCategory2())
//                  .video_url(boardArg.getVideoUrl())
//                  .thumbnail_url(boardArg.getThumbnailUrl())
//                  .isPublic(boardArg.getIsPublic())
//                  .build();
//            });
//
//    // when
//    ResponseEntity<ResultResponse<Pagination<EntityModel<BoardResponse>>>> responseEntity =
//        boardController.findAllBoard(0, 30, authorizationHeader);
//    // then
//    mockMvc.perform(get(BASE_PATH + "/lists")).andDo(print()).andExpect(status().isOk());
//  }
//
//  @Test
//  public void findBoardByCategory() throws Exception {
//    // given
//    String authorizationHeader = "Bearer <token>";
//    String category = "IT";
//    int page = 0;
//    int size = 30;
//
//    User user =
//        User.builder()
//            .email("tester1@example.com")
//            .password("testPassword")
//            .nickname("tester1")
//            .build();
//    Board board =
//        Board.builder()
//            .user(user)
//            .title("Test Board")
//            .content("Test Content")
//            .category1(Category.IT)
//            .category2(Category.KOTLIN)
//            .videoUrl("http://example.com/video")
//            .thumbnail_url("http://example.com/thumbnail")
//            .isPublic(YnType.Y)
//            .build();
//
//    List<Board> boardList = List.of(board);
//    Page<Board> boardPage = new PageImpl<>(boardList);
//
//    given(boardService.findBoardByCategory(any(), any())).willReturn(boardPage);
//    given(boardMapper.toDto(any(Board.class)))
//        .willAnswer(
//            invocation -> {
//              Board boardArg = invocation.getArgument(0);
//              return BoardResponse.builder()
//                  .id(boardArg.getId())
//                  .title(boardArg.getTitle())
//                  .content(boardArg.getContent())
//                  .category1(boardArg.getCategory1())
//                  .category2(boardArg.getCategory2())
//                  .video_url(boardArg.getVideoUrl())
//                  .thumbnail_url(boardArg.getThumbnailUrl())
//                  .isPublic(boardArg.getIsPublic())
//                  .build();
//            });
//
//    // when
//    ResponseEntity<ResultResponse<Pagination<EntityModel<BoardResponse>>>> responseEntity =
//        boardController.findBoardByCategory(category, page, size, authorizationHeader);
//
//    // then
//    mockMvc
//        .perform(
//            get(BASE_PATH + "/lists/category")
//                .param("category", category)
//                .param("page", String.valueOf(page))
//                .param("size", String.valueOf(size))
//                .header("Authorization", authorizationHeader))
//        .andDo(print())
//        .andExpect(status().isOk());
//  }
//
//  @Test
//  public void findBoardByUserNickname() throws Exception {
//    // given
//    String authorizationHeader = "Bearer <token>";
//    String nickname = "tester";
//    int page = 0;
//    int size = 30;
//
//    User user =
//        User.builder()
//            .id(1L)
//            .email("tester1@example.com")
//            .password("testPassword")
//            .nickname(nickname)
//            .build();
//    Board board =
//        Board.builder()
//            .user(user)
//            .title("Test Board")
//            .content("Test Content")
//            .category1(Category.IT)
//            .category2(Category.KOTLIN)
//            .videoUrl("http://example.com/video")
//            .thumbnail_url("http://example.com/thumbnail")
//            .isPublic(YnType.Y)
//            .build();
//
//    List<Board> boardList = List.of(board);
//    Page<Board> boardPage = new PageImpl<>(boardList);
//
//    given(boardService.findBoardByUserNickname(eq(nickname), any())).willReturn(boardPage);
//    given(userService.getUserByNickname(eq(nickname))).willReturn(user.getId());
//    given(boardMapper.toDto(eq(board)))
//        .willAnswer(
//            invocation -> {
//              Board boardArg = invocation.getArgument(0);
//              return BoardResponse.builder()
//                  .id(boardArg.getId())
//                  .title(boardArg.getTitle())
//                  .content(boardArg.getContent())
//                  .category1(boardArg.getCategory1())
//                  .category2(boardArg.getCategory2())
//                  .video_url(boardArg.getVideoUrl())
//                  .thumbnail_url(boardArg.getThumbnailUrl())
//                  .isPublic(boardArg.getIsPublic())
//                  .user_id(boardArg.getUser().getId())
//                  .nickname(boardArg.getUser().getNickname())
//                  .build();
//            });
//
//    // when
//    ResponseEntity<ResultResponse<Pagination<EntityModel<BoardResponse>>>> responseEntity =
//        boardController.findBoardByUserId(nickname, page, size, authorizationHeader);
//
//    // then
//    mockMvc
//        .perform(
//            get(BASE_PATH + "/lists/@{nickname}", nickname)
//                .param("page", String.valueOf(page))
//                .param("size", String.valueOf(size))
//                .header("Authorization", authorizationHeader))
//        .andDo(print())
//        .andExpect(status().isOk());
//  }
//
//  @Test
//  public void uploadVideo() throws Exception {
//    // given
//    MockMultipartFile videoFile =
//        new MockMultipartFile("video", "video.mp4", "video/mp4", "video data".getBytes());
//    MockMultipartFile thumbnailFile =
//        new MockMultipartFile(
//            "thumbnail", "thumbnail.jpeg", "image/jpeg", "thumbnail data".getBytes());
//
//    UploadFileResponse uploadFileResponse =
//        UploadFileResponse.builder()
//            .video_url("http://voilio.com/video.mp4")
//            .thumbnail_url("http://voilio.com/thumbnail.jpeg")
//            .build();
//
//    given(boardService.uploadFiles(any(MultipartFile.class), any(MultipartFile.class)))
//        .willReturn(uploadFileResponse);
//
//    // when, then
//    mockMvc
//        .perform(
//            MockMvcRequestBuilders.multipart(BASE_PATH + "/files")
//                .file(videoFile)
//                .file(thumbnailFile))
//        .andExpect(status().isCreated())
//        .andDo(print());
//  }
//}
