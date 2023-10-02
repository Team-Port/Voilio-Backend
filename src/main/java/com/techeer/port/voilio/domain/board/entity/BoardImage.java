package com.techeer.port.voilio.domain.board.entity;

import com.techeer.port.voilio.global.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_images")
public class BoardImage extends BaseEntity {

    @Id
    @Column(name = "board_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String url;
}
