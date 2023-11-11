package com.techeer.port.voilio.domain.user.service;

import com.techeer.port.voilio.domain.user.dto.UserSearchDto;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.entity.UserSearch;
import com.techeer.port.voilio.domain.user.mapper.UserSearchMapper;
import com.techeer.port.voilio.domain.user.repository.UserSearchRepository;
import com.techeer.port.voilio.global.error.ErrorCode;
import com.techeer.port.voilio.global.error.exception.BusinessException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSearchService {

  private final UserSearchRepository userSearchRepository;

  public List<UserSearchDto> findByUserId(User user) {
    List<UserSearch> userSearchList =
        userSearchRepository.findAllByUserIdOrderByUpdateAtDesc(user.getId());
    List<UserSearchDto> userSearchDtoList = UserSearchMapper.INSTANCE.toDtos(userSearchList);
    return userSearchDtoList;
  }

  @Transactional
  public UserSearchDto create(User user, String searchKeyword) {
    Optional<UserSearch> optionalUserSearch =
        userSearchRepository.findByUserIdAndContent(user.getId(), searchKeyword);
    if (optionalUserSearch.isPresent()) {
      optionalUserSearch.get().setUpdateAt(LocalDateTime.now());
      return UserSearchMapper.INSTANCE.toDto(optionalUserSearch.get());
    }
    UserSearch userSearch = new UserSearch(user.getId(), searchKeyword);
    userSearchRepository.save(userSearch);
    return UserSearchMapper.INSTANCE.toDto(userSearch);
  }

  @Transactional
  public void deleteById(Long id) {
    UserSearch userSearch =
        userSearchRepository
            .findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_SEARCH_KEYWORD));
    userSearchRepository.deleteById(userSearch.getId());
  }
}
