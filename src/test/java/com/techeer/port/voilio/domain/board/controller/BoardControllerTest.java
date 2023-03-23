package com.techeer.port.voilio.domain.board.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
    mockMvc.perform(patch("/boards/{boardId}", boardId)).andExpect(status().isOk());
    verify(boardService, times(1)).deleteBoard(boardId);
  }
}
