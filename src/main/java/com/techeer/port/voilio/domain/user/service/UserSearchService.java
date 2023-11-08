package com.techeer.port.voilio.domain.user.service;


import com.techeer.port.voilio.domain.user.dto.UserSearchDto;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.domain.user.entity.UserSearch;
import com.techeer.port.voilio.domain.user.mapper.UserSearchMapper;
import com.techeer.port.voilio.domain.user.repository.UserSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSearchService {

    private final UserSearchRepository userSearchRepository;

    public List<UserSearchDto> findByUserId(User user){
        List<UserSearch> userSearchList = userSearchRepository.findAllByUserIdOrderByUpdateAtDesc(user.getId());
        List<UserSearchDto> userSearchDtoList = UserSearchMapper.INSTANCE.toDtos(userSearchList);
        return userSearchDtoList;
    }
}
