package com.techeer.port.voilio.domain.board.repository;

import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

 @ExtendWith(SpringExtension.class)
 @ActiveProfiles("test")
 @DataJpaTest
public class BoardRepositoryTest {
      @Autowired
      private BoardRepository boardRepository;

      @Autowired
      private UserRepository userRepository;

      @BeforeEach
      public void saveTest(){
          User user1 = userRepository.save(User.builder()
                  .email("tester1@example.com")
                  .password("testPassword")
                  .nickname("tester1")
                  .build());

          User user2 = userRepository.save(User.builder()
                  .email("tester2@example.com")
                  .password("testPassword")
                  .nickname("tester2")
                  .build());

          Board board1 = boardRepository.save(Board.builder()
                  .user(user1)
                  .title("testTitle")
                  .content("testContent")
                  .category1(Category.IT)
                  .category2(Category.IT)
                  .video_url("https://www.naver.com/")
                  .thumbnail_url("https://www.naver.com")
                  .build());

          Board board2 = boardRepository.save(Board.builder()
                  .user(user1)
                  .title("testTitle2")
                  .content("testContent2")
                  .category1(Category.IT)
                  .category2(Category.IT)
                  .video_url("https://www.naver.com/")
                  .thumbnail_url("https://www.naver.com")
                  .build());

          Board board3 = boardRepository.save(Board.builder()
                  .user(user1)
                  .title("testTitle3")
                  .content("testContent3")
                  .category2(Category.IT)
                  .category1(Category.IT)
                  .video_url("https://www.naver.com/")
                  .thumbnail_url("https://www.naver.com")
                  .build());
      }

      @Test
      @DisplayName("deleteBoardTest Start")
      public void deleteBoardTest(){
          Long boardId = 1L;

          //given
          List<Board> boardList = boardRepository.findAll();
          int boardSize = boardList.size();
          Board board = boardRepository.findById(boardId).orElseThrow();
          assertFalse(board.getIsDeleted());

          //when
          board.changeDeleted();
          boardRepository.save(board);

          //then
          boardList = boardRepository.findAll();
          assertEquals(boardList.size(),boardSize);
          board = boardRepository.findById(boardId).orElseThrow();
          assertTrue(board.getIsDeleted());
      }
}
