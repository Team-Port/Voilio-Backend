package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.dto.request.BoardUpdateRequest;
import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.minidev.asm.ex.ConvertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final BoardMapper boardMapper;
  private final S3Manager s3Manager;

  public void deleteBoard(Long board_id) {
    Board board = boardRepository.findById(board_id).orElseThrow(NotFoundBoard::new);
    board.changeDeleted();
    boardRepository.save(board);
  }

  public BoardResponse findBoardById(Long board_id) {
    Board board = boardRepository.findBoardById(board_id).orElseThrow(NotFoundBoard::new);
    return boardMapper.toDto(board);
  }

  @Transactional
  public void createBoard(BoardCreateRequest boardCreateRequest) {
    User user =
        userRepository.findById(boardCreateRequest.getUser_id()).orElseThrow(NotFoundUser::new);
    boardRepository.save(boardCreateRequest.toEntity(user));
  }

  public void hideBoard(Long board_id) {
    Board board = boardRepository.findById(board_id).orElseThrow(NotFoundBoard::new);
    board.changePublic();
    boardRepository.save(board);
  }

  public List<BoardResponse> findBoardByKeyword(String keyword) {
    List<Board> boards = boardRepository.findBoardByKeyword(keyword);
    if (boards.isEmpty()) throw new NotFoundBoard();
    return boardMapper.toDto(boards);
  }

  @Transactional
  public Board updateBoard(Long board_id, BoardUpdateRequest request) {
    Board board = boardRepository.findById(board_id).orElseThrow(NotFoundBoard::new);
    if (board.getIsDeleted() == true) throw new NotFoundBoard();
    return boardRepository.save(request.toEntity(board));
  }

  public Page<Board> findAllBoard(Pageable pageable) {
    Page<Board> result = boardRepository.findAllBoard(pageable);
    if (result.isEmpty()) {
      throw new NotFoundBoard();
    }
    return result;
  }

  public Page<Board> findBoardByCategory(Category category, Pageable pageable) {
    Page<Board> result = boardRepository.findBoardByCategory(category, category, pageable);
    if (result.isEmpty()) {
      throw new NotFoundBoard();
    }
    return result;
  }

  public Page<Board> findBoardByUserNickname(String nickname, Pageable pageable) {
    User user = userRepository.findUserByNickname(nickname);
    Page<Board> result = boardRepository.findBoardByUserNickname(nickname, pageable);

    if (result.isEmpty()) {
      if (user == null) throw new NotFoundUser();
      else throw new NotFoundBoard();
    }
    return result;
  }

  public UploadFileResponse uploadFiles(MultipartFile videoFile, MultipartFile thumbnailFile) {
    try {
      return boardMapper.toDto(
          s3Manager.upload(videoFile, "video"), s3Manager.upload(thumbnailFile, "thumbnail"));
    } catch (IOException e) {
      throw new ConvertException();
    }
  }
}
