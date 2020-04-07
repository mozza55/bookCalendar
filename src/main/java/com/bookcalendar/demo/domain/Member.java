package com.bookcalendar.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "USERID_UNIQUE",
        columnNames = {"userid"})
})
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userid;

    private String password;

    private String username;

    private String nickname;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private Inventory inventory;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private Calendar calendar;

    public static Member createMember(String userid, String password, String username, String nickname) {
        Member member = new Member();
        member.setUserid(userid);
        member.setPassword(password);
        member.setUsername(username);
        member.setNickname(nickname);
        return member;
    }
    //연관관계 편의메서드
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
        inventory.setMember(this);
    }

    //연관 관계 편의 메서드
    public void setCalenar(Calendar calendar){
        this.calendar = calendar;
        calendar.setMember(this);
    }

}
