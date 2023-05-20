package com.techeer.port.voilio.domain.board.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.dto.request.BoardUpdateRequest;
import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.dto.response.UpdateFileResponse;
import com.techeer.port.voilio.domain.board.dto.response.UploadFileResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.service.BoardService;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.service.UserService;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.result.ResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@DisplayName("BoardController Test Start")
public class BoardControllerTest {

  @Mock
  private BoardService boardService;
  @Mock
  private UserService userService;

  @InjectMocks
  private BoardController boardController;
  private MockMvc mockMvc;

  private static String BASE_PATH = "http://localhost/api/v1/boards";

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
  }

  @Test
  public void softDeleteBoard() throws Exception {
    Long boardId = 1L;

    // given,when
    doNothing().when(boardService).deleteBoard(boardId);

    // then
    mockMvc
        .perform(delete(BASE_PATH + "/{boardId}", boardId))
        .andDo(print())
        .andExpect(status().isOk());
    verify(boardService, times(1)).deleteBoard(boardId);
  }

  @Test
  public void findBoardById_authenticatedUser() throws Exception {
    //given
    Long boardId = 1L;
    String authorizationHeader = "Bearer <token>";
    Long currentLoginUserId = 123L;

    BoardResponse boardResponse = BoardResponse.builder()
        .id(boardId)
        .title("Test")
        .content("Test")
        .category1(Category.IT)
        .category2(Category.JAVA)
        .video_url("https://www.naver.com/video.mp4")
        .thumbnail_url("https://www.naver.com/thumbnail.jpeg")
        .user_id(123L)
        .nickname("tester")
        .isPublic(false)
        .build();

    given(boardService.findBoardByIdExceptHide(boardId))
        .willReturn(boardResponse);
    given(userService.getCurrentLoginUser(authorizationHeader))
        .willReturn(currentLoginUserId);
    given(userService.getUserIdByBoardId(boardId))
        .willReturn(currentLoginUserId);

    //when
    ResponseEntity<EntityModel<ResultResponse<Board>>> responseEntity = boardController.findBoardById(
        boardId, authorizationHeader);

    //then
    mockMvc
        .perform(
            get(BASE_PATH + "/{board_id}", boardId)
                .header("Authorization", authorizationHeader))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void findBoardById_unauthenticatedUser() throws Exception {
    //given
    Long boardId = 1L;
    String authorizationHeader = "";

    BoardResponse boardResponse = BoardResponse.builder()
        .id(boardId)
        .title("Test")
        .content("Test")
        .category1(Category.IT)
        .category2(Category.JAVA)
        .video_url("https://www.naver.com/video.mp4")
        .thumbnail_url("https://www.naver.com/thumbnail.jpeg")
        .user_id(123L)
        .nickname("tester")
        .isPublic(false)
        .build();

    given(boardService.findBoardById(boardId))
        .willReturn(boardResponse);
    given(userService.getCurrentLoginUser(authorizationHeader))
        .willReturn(null);

    //when
    ResponseEntity<EntityModel<ResultResponse<Board>>> responseEntity = boardController.findBoardById(
        boardId, authorizationHeader);

    //then
    mockMvc
        .perform(
            get(BASE_PATH + "/{board_id}", boardId)
                .header("Authorization", authorizationHeader))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
