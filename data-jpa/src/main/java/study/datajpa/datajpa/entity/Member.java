package study.datajpa.datajpa.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "userName", "age"})
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String userName;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String userName, int age, Team team) {
        this.userName = userName;
        this.age = age;
        if (this.team == null){
            changeTeam(team);
        }
    }


    // 연관관계 편의 메소드
    protected void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

}
