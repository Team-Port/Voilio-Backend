package com.techeer.port.voilio.domain.subscribe.entity;

import com.techeer.port.voilio.domain.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "subscribes")
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

//    @Column(name = "user_nickname", insertable = false, updatable = false)
//    private String user_nickname;
//    @Column(name = "follower_nickname", insertable = false, updatable = false)
//    private String follower_nickname;
//
//    public String getUserNickname() {
//        return user.getNickname();
//    }
//    public String getfollowerNickname() {
//        return follower.getNickname();
//    }

}
