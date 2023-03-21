package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void saveTest(){
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
        boardRepository.save(board1);
        boardRepository.save(board2);
    }

    @Test
    @DisplayName("deleteBoardTest Start")
    public void deleteBoardTest(){
        List<Board> boardList = boardRepository.findAll();
        int boardSize = boardList.size();

        //when
        boardRepository.deleteById(1);

        //then
        boardList = boardRepository.findAll();
        assertEquals(boardList.size(),boardSize);
        Board board = boardRepository.findById(1).orElseThrow();
        assertTrue(board.getIsDeleted());
    }
}
