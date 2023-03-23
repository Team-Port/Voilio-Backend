package com.techeer.port.voilio.domain.board.repository;

import static org.junit.jupiter.api.Assertions.*;

// @ExtendWith(SpringExtension.class)
// @ActiveProfiles("test")
// @DataJpaTest
public class BoardRepositoryTest {
  //    @Autowired
  //    private BoardRepository boardRepository;
  //
  //    @BeforeEach
  //    public void saveTest(){
  //        Board board1 = Board.builder()
  //                .userId(1)
  //                .title("testTitle")
  //                .content("testContent")
  //                .video("https://www.naver.com/")
  //                .thumbnail("https://www.naver.com")
  //        .build();
  //        Board board2 = Board.builder()
  //                .userId(1)
  //                .title("testTitle2")
  //                .content("testContent2")
  //                .video("https://www.naver.com/")
  //                .thumbnail("https://www.naver.com")
  //                .build();
  //        boardRepository.save(board1);
  //        boardRepository.save(board2);
  //    }
  //
  //    @Test
  //    @DisplayName("deleteBoardTest Start")
  //    public void deleteBoardTest(){
  //        //given
  //        List<Board> boardList = boardRepository.findAll();
  //        int boardSize = boardList.size();
  //        Board board = boardRepository.findById(1).orElseThrow();
  //        assertFalse(board.getIsDeleted());
  //
  //        //when
  //        boardRepository.deleteById(1);
  //
  //        //then
  //        boardList = boardRepository.findAll();
  //        assertEquals(boardList.size(),boardSize);
  //        board = boardRepository.findById(1).orElseThrow();
  //        assertTrue(board.getIsDeleted());
  //
  //    }
}
