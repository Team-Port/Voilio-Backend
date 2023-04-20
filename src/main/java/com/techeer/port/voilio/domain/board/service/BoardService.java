package com.techeer.port.voilio.domain.board.service;

import static com.techeer.port.voilio.global.result.ResultCode.BOARD_FINDALL_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.techeer.port.voilio.domain.board.controller.BoardController;
import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.dto.request.BoardUpdateRequest;
import com.techeer.port.voilio.domain.board.dto.response.BoardFindAllDevResponse;
import com.techeer.port.voilio.domain.board.dto.response.BoardResponse;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.mapper.AllMapper;
import com.techeer.port.voilio.global.result.ResultResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final BoardMapper boardMapper;
  private final AllMapper allMapper;

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

  public ResultResponse<List<EntityModel<Board>>> findAllBoard() {
    List<EntityModel<BoardFindAllDevResponse>> boardEntities =
        allMapper.toDto(boardRepository.findAll()).stream()
            .map(
                boardFindAllDevResponse ->
                    EntityModel.of(
                        boardFindAllDevResponse,
                        linkTo(
                                methodOn(BoardController.class)
                                    .findBoardById(boardFindAllDevResponse.getBoard().getId()))
                            .withSelfRel()))
            .collect(Collectors.toList());
    return new ResultResponse<>(BOARD_FINDALL_SUCCESS, boardEntities);
  }
}
