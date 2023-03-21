package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.global.common.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

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
    void softDeleteBoard_whenBoardExists_shouldDeleteBoard(){
        //given
        Board board1 = Board.builder()
                .userId(1)
                .title("testTitle")
                .content("testContent")
                .video("https://www.naver.com/")
                .thumbnail("https://www.naver.com")
                .build();
        given(boardRepository.findById(1)).willReturn(Optional.of(board1));

        //when
        boardService.deleteBoard(1);

        //then
        verify(boardRepository,times(1)).save(board1);
        assertTrue(board1.getIsDeleted());
    }

    @Test
    public void deleteBoard_whenBoardNotFound_shouldThrowNotFoundException(){
        //given
        given(boardRepository.findById(1)).willReturn(Optional.empty());

        //when, then
        assertThrows(NotFoundException.class,() ->boardService.deleteBoard(1));
    }
}
