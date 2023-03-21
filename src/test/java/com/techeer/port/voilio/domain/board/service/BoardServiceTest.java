package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@DisplayName("Board Service")
public class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    @DisplayName("mockito service test")
    void mockBoardServiceTest(){
        //given
        Board board1 = Board.builder()
                .userId(1)
                .title("testTitle")
                .content("testContent")
                .video("https://www.naver.com/")
                .thumbnail("https://www.naver.com")
                .build();
        Board board2 = Board.builder()
                .userId(1)
                .title("testTitle2")
                .content("testContent2")
                .video("https://www.naver.com/")
                .thumbnail("https://www.naver.com")
                .build();
        List<Board> boardList = new ArrayList<>();
        boardList.add(board1);
        boardList.add(board2);
        given(boardRepository.findAll()).willReturn(boardList);

        //when
        boardService.deleteBoard(1);

        List<Board> afterBoardList = boardRepository.findAll();
        //then
        assertEquals(afterBoardList.size(),2);
    }
}
