package com.techeer.port.voilio.domain.like.entity;


import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.LikeDivision;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    private Long contentId;

    @Enumerated(EnumType.STRING)
    private LikeDivision division;
}
