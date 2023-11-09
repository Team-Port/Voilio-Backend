package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.board.dto.BoardThumbnailDto;
import com.techeer.port.voilio.domain.board.dto.BoardVideoDto;
import com.techeer.port.voilio.domain.board.dto.request.BoardCreateRequest;
import com.techeer.port.voilio.domain.board.dto.request.BoardUpdateRequest;
import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.entity.BoardImage;
import com.techeer.port.voilio.domain.board.exception.NotFoundBoard;
import com.techeer.port.voilio.domain.board.exception.NotFoundUser;
import com.techeer.port.voilio.domain.board.mapper.BoardMapper;
import com.techeer.port.voilio.domain.board.repository.BoardCustomRepository;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.like.likeService.LikeService;
import com.techeer.port.voilio.domain.like.repository.LikeRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.mapper.UserMapper;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.LikeDivision;
import com.techeer.port.voilio.global.common.YnType;
import com.techeer.port.voilio.s3.util.S3Manager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.minidev.asm.ex.ConvertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

  private final LikeService likeService;
  private final BoardRepository boardRepository;
  private final BoardCustomRepository boardCustomRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final S3Manager s3Manager;

  public void deleteBoard(Long board_id) {
    Board board = boardRepository.findById(board_id).orElseThrow(NotFoundBoard::new);
    board.changeDelYn(YnType.Y);
    boardRepository.save(board);
  }

  public Page<BoardDto> findAllBoard(Pageable pageable) {
    Page<Board> boardPage =
        boardRepository.findAllByDelYnAndIsPublicOrderByUpdateAtDesc(pageable, YnType.N, YnType.Y);

    List<BoardDto> boardDtoList = new ArrayList<>();

    for (Board board : boardPage) {

      // user 정보 넣기
      BoardDto boardDto = BoardMapper.INSTANCE.toDto(board);
      User user = board.getUser();
      boardDto.setUserSimpleDto(UserMapper.INSTANCE.toSimpleDto1(user));

      // 좋아요 개수 넣기
      Long likeCount = likeService.getLikeCount(LikeDivision.BOARD_LIKE, boardDto.getId());
      boardDto.setLikeCount(likeCount);

      boardDtoList.add(boardDto);
    }

    return new PageImpl<>(boardDtoList, pageable, boardPage.getTotalElements());
  }

  public BoardDto findBoardById(Long boardId, User user) {
    Board board = boardRepository.findBoardById(boardId).orElseThrow(NotFoundBoard::new);
    return BoardMapper.INSTANCE.toDto(board);
  }

  public Page<BoardDto> findBoardByUser(User user, Long userId, Pageable pageable) {

    if (user == null || user.getId() != userId) {
      User foundUser = userRepository.findById(userId).orElseThrow(NotFoundUser::new);

      Page<Board> boards =
          boardRepository.findBoardsByDelYnAndIsPublicAndUserOrderByUpdateAtDesc(
              pageable, YnType.N, YnType.Y, foundUser);

      Page<BoardDto> boardDtoPage = BoardMapper.INSTANCE.toPageList(boards);
      return boardDtoPage;

    } else {
      User foundUser = userRepository.findById(userId).orElseThrow(NotFoundUser::new);

      Page<Board> boards =
          boardRepository.findBoardsByDelYnAndUserOrderByUpdateAtDesc(
              pageable, YnType.N, foundUser);
      Page<BoardDto> boardDtoPage = BoardMapper.INSTANCE.toPageList(boards);
      return boardDtoPage;
    }
  }

  //  public BoardDto findBoardByIdExceptHide(Long board_id) {
  //    Board board =
  // boardRepository.findBoardByIdExceptHide(board_id).orElseThrow(NotFoundBoard::new);
  //    return BoardMapper.INSTANCE.toDto(board);
  //  }

  @Transactional
  public Board createBoard(BoardCreateRequest boardCreateRequest, User user) {

    Board board = BoardMapper.INSTANCE.toEntityDto(boardCreateRequest, user);

    List<BoardImage> boardImageList = new ArrayList<>();
    List<String> boardImageUrls = boardCreateRequest.getBoardImageUrls();

    // 게시글 이미지 url BoardImage에 등록하기
    for (String url : boardImageUrls) {
      BoardImage boardImage = new BoardImage(board, url);
      boardImageList.add(boardImage);
    }

    board.setBoardImages(boardImageList);

    boardRepository.save(board);
    return board;
  }

  public void hideBoard(Long board_id) {
    Board board = boardRepository.findById(board_id).orElseThrow(NotFoundBoard::new);
    board.changePublic();
    boardRepository.save(board);
  }

  public Page<BoardDto> findBoardByKeyword(Pageable pageable, String keyword) {

    Page<Board> boards =
        boardCustomRepository.findBoardByKeyword(keyword, YnType.N, YnType.Y, pageable);

    return BoardMapper.INSTANCE.toPageList(boards);
  }

  @Transactional
  public Board updateBoard(Long board_id, BoardUpdateRequest request) {
    Board board = boardRepository.findById(board_id).orElseThrow(NotFoundBoard::new);
    if (board.getDelYn().equals(YnType.Y)) {
      throw new NotFoundBoard();
    }
    return boardRepository.save(request.toEntity(board));
  }

  public Page<BoardDto> findBoardByCategory(Category category, Pageable pageable) {
    Page<Board> result =
        boardRepository.findByDelYnAndIsPublicAndCategory1OrCategory2OrderByCreateAtDesc(
            YnType.N, YnType.Y, category, category, pageable);

    Page<BoardDto> pageDtos = BoardMapper.INSTANCE.toPageList(result);

    return pageDtos;
  }

  public Page<Board> findBoardByUserNicknameExceptHide(String nickname, Pageable pageable) {
    Optional<User> user = userRepository.findUserByNicknameAndDelYn(nickname, YnType.N);
    Page<Board> result = boardRepository.findBoardByUserNicknameExceptHide(nickname, pageable);

    if (result.isEmpty()) {
      if (user == null) {
        throw new NotFoundUser();
      } else {
        throw new NotFoundBoard();
      }
    }
    return result;
  }

  public BoardVideoDto uploadVideo(MultipartFile videoFile) {
    try {
      return BoardMapper.INSTANCE.toVideo(s3Manager.upload(videoFile, "video"));
    } catch (IOException e) {
      throw new ConvertException();
    }
  }

  public BoardThumbnailDto uploadThumbnail(MultipartFile thumbnailFile) {
    try {
      return BoardMapper.INSTANCE.toThumbnail(s3Manager.upload(thumbnailFile, "thumbnail"));
    } catch (IOException e) {
      throw new ConvertException();
    }
  }
}
