package com.techeer.port.voilio.domain.board.entity;

import com.sun.istack.NotNull;
import com.techeer.port.voilio.global.common.BaseEntity;
import com.techeer.port.voilio.global.common.Category;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.net.URL;

@Entity
@Getter
@Builder
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board extends BaseEntity,RepresentationModel<Board> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @NotNull
    @Column
    private Long userId;

    @NotNull
    @Column
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category1;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category2;

    @Column
    private String content;

    @Column
    private String video;

    @Column
    private String thumbnail;

    @Column
    private boolean isPublic;

}
