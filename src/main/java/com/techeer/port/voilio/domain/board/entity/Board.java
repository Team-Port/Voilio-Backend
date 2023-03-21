package com.techeer.port.voilio.domain.board.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer board_id;

    private String title;

    private String content;

    @ElementCollection(targetClass = Integer.class)
    private List<Integer> category;

    private String video_url;

    private String thumbnail_url;

    @Column
    private Boolean isPublic;

    @Column(nullable = false)
    private Boolean isDeleted;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Board(Integer board_id, String title, String content, List<Integer> category, String video_url,
        String thumbnail_url, boolean isPublic, boolean isDeleted) {
        this.board_id = board_id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.video_url = video_url;
        this.thumbnail_url = thumbnail_url;
        this.isPublic = isPublic;
        this.isDeleted = isDeleted;
    }
}

