package com.techeer.port.voilio.domain.user.entity;


import com.techeer.port.voilio.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_searchs")
public class UserSearch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_search_id")
    private Long id;

    private Long userId;

    private String content;
}
