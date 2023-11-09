package com.techeer.port.voilio.domain.board.entity;

import com.techeer.port.voilio.global.common.BaseEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board_images")
public class BoardImage extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_image_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  private String url;

  public BoardImage(Board board, String url) {
    this.board = board;
    this.url = url;
  }
}
