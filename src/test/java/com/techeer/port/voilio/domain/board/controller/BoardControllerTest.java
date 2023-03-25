package com.techeer.port.voilio.domain.board.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.techeer.port.voilio.domain.board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@DisplayName("BoardController Test Start")
public class BoardControllerTest {

  @Mock private BoardService boardService;

  @InjectMocks private BoardController boardController;
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
  public void findBoardById() throws Exception {
    Long boardId = 1L;

//     given,when
    doNothing().when(boardService).findBoardById(boardId);

    mockMvc
        .perform(get(BASE_PATH+"/{boardId}",boardId))
        .andDo(print())
        .andExpect(status().isOk());

    verify(boardService,times(1)).findBoardById(boardId);
  }
}
