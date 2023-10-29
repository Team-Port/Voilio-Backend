package com.techeer.port.voilio.domain.like.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.techeer.port.voilio.domain.like.entity.Like;
import com.techeer.port.voilio.domain.user.entity.User;
import com.techeer.port.voilio.global.common.LikeDivision;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.techeer.port.voilio.domain.like.entity.QLike.like;

@Repository
@RequiredArgsConstructor
public class LikeCustomRepositoryImpl implements LikeCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Like> findByDivisionAndUser(LikeDivision division, User user, Pageable pageable) {
        List<Like> likeList = jpaQueryFactory
                .selectFrom(like)
                .where(
                        like.division.eq(division)
                        , like.user.eq(user)
                )
                .orderBy(like.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
        Long count = jpaQueryFactory
                .select(like.count())
                .from(like)
                .where(
                        like.division.eq(division)
                        , like.user.eq(user)
                )
                .fetchFirst();
        return new PageImpl<>(likeList, pageable, count);
    }
}
