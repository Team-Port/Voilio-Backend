package com.techeer.port.voilio.domain.board.service;

import com.techeer.port.voilio.domain.board.dto.BoardDto;
import com.techeer.port.voilio.domain.board.dto.BoardSimpleDto;
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
import com.techeer.port.voilio.domain.board.repository.BoardImageRepository;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.domain.like.likeService.LikeService;
import com.techeer.port.voilio.domain.like.repository.LikeRepository;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.repository.UserRepository;
import com.techeer.port.voilio.global.common.Category;
import com.techeer.port.voilio.global.common.LikeDivision;
import com.techeer.port.voilio.global.common.UploadDivision;
import com.techeer.port.voilio.global.common.YnType;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
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
  private final BoardImageRepository boardImageRepository;
  private final BoardCustomRepository boardCustomRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final S3Manager s3Manager;

  public Page<BoardDto> findAllBoard(Pageable pageable) {
    Page<Board> boardPage =
        boardRepository.findAllByDelYnAndIsPublicOrderByUpdateAtDesc(pageable, YnType.N, YnType.Y);

    List<BoardDto> boardDtoList = new ArrayList<>();

    for (Board board : boardPage) {

      BoardDto boardDto = BoardMapper.INSTANCE.toDto(board);

      // 좋아요 개수 넣기
      Long likeCount = likeService.getLikeCount(LikeDivision.BOARD_LIKE, boardDto.getId());
      boardDto.setLikeCount(likeCount);

      boardDtoList.add(boardDto);
    }
    return new PageImpl<>(boardDtoList, pageable, boardPage.getTotalElements());
  }

  public BoardSimpleDto findBoardById(Long boardId, User user) {

    Board board = boardRepository.findBoardById(boardId).orElseThrow(NotFoundBoard::new);
    BoardSimpleDto boardSimpleDto = BoardMapper.INSTANCE.toSimpleDto(board);

    // 게시글을 작성한 유저의 좋아요 여부 표시
    // 로그인 하지 않거나 유저가 같지 않을 경우 false 반환
    if (user != null && board.getUser().getId() == user.getId()) {
      boolean existsLikeByDivisionAndContentId =
          likeRepository.existsLikeByDivisionAndContentIdAndUser(
              LikeDivision.BOARD_LIKE, boardSimpleDto.getId(), user);
      boardSimpleDto.updateIsLiked(existsLikeByDivisionAndContentId);
    } else {
      boardSimpleDto.updateIsLiked(false);
    }
    // 좋아요 개수 넣기
    Long likeCount = likeService.getLikeCount(LikeDivision.BOARD_LIKE, boardSimpleDto.getId());
    boardSimpleDto.updateLikeCount(likeCount);

    // 조회수 증가
    board.addView();

    return boardSimpleDto;
  }

  public Page<BoardDto> findBoardByUser(User user, Long userId, Pageable pageable) {

    List<BoardDto> boardDtoList = new ArrayList<>();

    if (user == null || user.getId() != userId) {
      User foundUser = userRepository.findById(userId).orElseThrow(NotFoundUser::new);

      Page<Board> boardPage =
          boardRepository.findBoardsByDelYnAndIsPublicAndUserOrderByUpdateAtDesc(
              pageable, YnType.N, YnType.Y, foundUser);

      for (Board board : boardPage) {
        BoardDto boardDto = BoardMapper.INSTANCE.toDto(board);
        Long likeCount = likeService.getLikeCount(LikeDivision.BOARD_LIKE, boardDto.getId());
        boardDto.updateLikeCount(likeCount);
        boardDto.updateIsLiked(false);

        boardDtoList.add(boardDto);
      }

      return new PageImpl<>(boardDtoList, pageable, boardPage.getTotalElements());

    } else {
      User foundUser = userRepository.findById(userId).orElseThrow(NotFoundUser::new);

      Page<Board> boardPage =
          boardRepository.findBoardsByDelYnAndUserOrderByUpdateAtDesc(
              pageable, YnType.N, foundUser);

      for (Board board : boardPage) {

        BoardDto boardDto = BoardMapper.INSTANCE.toDto(board);
        Long likeCount = likeService.getLikeCount(LikeDivision.BOARD_LIKE, boardDto.getId());
        boardDto.setLikeCount(likeCount);

        boolean existsLikeByDivisionAndContentId =
            likeRepository.existsLikeByDivisionAndContentIdAndUser(
                LikeDivision.BOARD_LIKE, boardDto.getId(), user);
        boardDto.setIsLiked(existsLikeByDivisionAndContentId);

        boardDtoList.add(boardDto);
      }

      return new PageImpl<>(boardDtoList, pageable, boardPage.getTotalElements());
    }
  }

  public Page<BoardDto> findBoardByCategoryAndKeyword(
      Category category, String keyword, Pageable pageable) {

    Page<Board> boardPage;

    if (category.toString().equals("ALL")) {
      boardPage = boardCustomRepository.findBoardByKeyword(keyword, YnType.N, YnType.Y, pageable);
    } else {
      boardPage =
          boardCustomRepository.findBoardByCategoryAndKeyword(
              category, keyword, YnType.N, YnType.Y, pageable);
    }

    List<BoardDto> boardDtoList = new ArrayList<>();

    for (Board board : boardPage) {

      BoardDto boardDto = BoardMapper.INSTANCE.toDto(board);

      // 좋아요 개수 넣기
      Long likeCount = likeService.getLikeCount(LikeDivision.BOARD_LIKE, boardDto.getId());
      boardDto.setLikeCount(likeCount);

      boardDtoList.add(boardDto);
    }

    return new PageImpl<>(boardDtoList, pageable, boardPage.getTotalElements());
  }

  public BoardDto findBoardByIdExceptHide(Long board_id) {
    Board board = boardRepository.findBoardByIdExceptHide(board_id).orElseThrow(NotFoundBoard::new);
    return BoardMapper.INSTANCE.toDto(board);
  }

  @Transactional
  public BoardDto createBoard(BoardCreateRequest boardCreateRequest, User user) {
    Board board = BoardMapper.INSTANCE.toEntity(boardCreateRequest);
    board.addUser(user);
    Board savedBoard = boardRepository.save(board);

    if (boardCreateRequest.getBoardImageUrls() != null
        || !boardCreateRequest.getBoardImageUrls().isEmpty()) {
      List<String> boardImageUrls = boardCreateRequest.getBoardImageUrls();

      // 게시글 이미지 url BoardImage에 등록하기
      for (String url : boardImageUrls) {
        BoardImage boardImage = new BoardImage(savedBoard, url);
        boardImageRepository.save(boardImage);
      }
    }
    return BoardMapper.INSTANCE.toDto(savedBoard);
  }

  public BoardDto hideBoard(Long boardId, User user) {
    Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoard::new);
    if (user != board.getUser()) {
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    board.changePublic();
    Board savedBoard = boardRepository.save(board);
    BoardDto boardDto = BoardMapper.INSTANCE.toDto(savedBoard);
    return boardDto;
  }

  public Page<BoardDto> findBoardByKeyword(Pageable pageable, String keyword) {

    Page<Board> boards =
        boardCustomRepository.findBoardByKeyword(keyword, YnType.N, YnType.Y, pageable);

    return BoardMapper.INSTANCE.toPageList(boards);
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

  public BoardThumbnailDto createImageUrl(MultipartFile imageFile, UploadDivision uploadDivision) {
    try {
      if (uploadDivision.equals(UploadDivision.THUMBNAIL)) {
        return BoardMapper.INSTANCE.toThumbnail(s3Manager.upload(imageFile, "image/thumbnail"));
      } else if (uploadDivision.equals(UploadDivision.BOARD)) {
        return BoardMapper.INSTANCE.toThumbnail(s3Manager.upload(imageFile, "image/board"));
      } else if (uploadDivision.equals(UploadDivision.PROFILE)) {
        return BoardMapper.INSTANCE.toThumbnail(s3Manager.upload(imageFile, "image/profile"));
      }
      throw new BusinessException(ErrorCode.INVALID_AUTH_TOKEN);

    } catch (IOException e) {
      throw new ConvertException();
    }
  }

  public void changeBoard(Long boardId, BoardUpdateRequest boardUpdateRequest) {

    Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoard::new);

    board.updateBoard(
        boardUpdateRequest.getTitle(),
        boardUpdateRequest.getContent(),
        boardUpdateRequest.getSummary(),
        boardUpdateRequest.getCategory1(),
        boardUpdateRequest.getCategory2(),
        boardUpdateRequest.getThumbnailUrl());

    List<BoardImage> existBoardImage = boardImageRepository.findByBoard(board);
    List<String> boardImageUrls = boardUpdateRequest.getBoardImageUrls();
    boardImageRepository.deleteAll(existBoardImage);

    for (String boardImageUrl : boardImageUrls) {
      BoardImage newBoardImage = new BoardImage(board, boardImageUrl);
      boardImageRepository.save(newBoardImage);
    }
  }

  public void deleteBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoard::new);
    board.changeDelYn(YnType.Y);
  }
}
