package study.datajpa.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String teamName;

    @OneToMany(mappedBy = "team")
    List<Member> members = new ArrayList<>();

    public Team(String teamName) {
        this.teamName = teamName;
    }
}
