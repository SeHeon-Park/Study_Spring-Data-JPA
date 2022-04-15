package study.datajpa.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.datajpa.dto.MemberDto;
import study.datajpa.datajpa.entity.Member;
import study.datajpa.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;


    @Test
    public void MemberRepositoryTest(){
        Team t1 = new Team("광교");
        teamRepository.save(t1);

        Member m1 = new Member("박세헌", 27, t1);
        memberRepository.save(m1);

        Member m2 = new Member("서지오", 24, t1);
        memberRepository.save(m2);

        List<Member> findMember = memberRepository.findByUserNameAndAgeGreaterThan("박세헌", 25);
        assertThat(findMember.get(0).getAge()).isGreaterThan(24);

        List<MemberDto> result = memberRepository.findDto();
        System.out.println(result);

        List<Member> memberByNames = memberRepository.findMemberByNames(Arrays.asList("박세헌", "서지오"));
        memberByNames.forEach(o-> System.out.println(o.toString()));
    }

    @Test
    public void findUser(){
        Member m1 = new Member("박세헌", 27);
        Member m2 = new Member("박세헌", 27);
        memberRepository.save(m1);
        memberRepository.save(m2);
        Optional<Member> s = memberRepository.findOptionalByUserName("박세헌").ofNullable(null);
        System.out.println(s);
    }

    @Test
    public void pagingTest(){
        memberRepository.save(new Member("박세헌1", 27));
        memberRepository.save(new Member("박세헌2", 27));
        memberRepository.save(new Member("박세헌3", 27));
        memberRepository.save(new Member("박세헌4", 27));
        memberRepository.save(new Member("박세헌5", 27));

        int age = 27;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));

        Page<Member> page = memberRepository.findPageByAge(age, pageRequest);

        Page<MemberDto> memberDto = page.map(o -> new MemberDto(o.getId(), o.getUserName(), o.getTeam().getTeamName()));
        // memberDto로 변환

        List<Member> content = page.getContent(); //조회된 데이터

        assertThat(content.size()).isEqualTo(3); //조회된 데이터 수
        assertThat(page.getTotalElements()).isEqualTo(5); //전체 데이터 수
        assertThat(page.getNumber()).isEqualTo(0); //페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호
        assertThat(page.isFirst()).isTrue(); //첫번째 항목인가?
        assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?

    }

    @Test
    public void bulkTest(){
        memberRepository.save(new Member("박세헌1", 16));
        memberRepository.save(new Member("박세헌2", 17));
        memberRepository.save(new Member("박세헌3", 18));
        memberRepository.save(new Member("박세헌4", 20));
        memberRepository.save(new Member("박세헌5", 30));

        int count = memberRepository.bulkAgePlus(20);

        assertThat(count).isEqualTo(2);
    }

    @Test
    public void entityGraphTest(){
        Team team1 = new Team("광교");
        Team team2 = new Team("용인");
        teamRepository.save(team1);
        teamRepository.save(team2);

        memberRepository.save(new Member("박세헌1", 16, team1));
        memberRepository.save(new Member("박세헌2", 17, team2));

        em.flush();
        em.clear();


        List<Member> result = memberRepository.findAll();
        for (Member member : result) {
            System.out.println(member);
            System.out.println(member.getTeam().getTeamName());
        }
    }

    @Test
    public void hintAndLockTest(){
        Team team1 = new Team("광교");
        Team team2 = new Team("용인");
        teamRepository.save(team1);
        teamRepository.save(team2);

        memberRepository.save(new Member("박세헌1", 16, team1));
        memberRepository.save(new Member("박세헌2", 17, team2));

        em.flush();
        em.clear();

        List<Member> m1 = memberRepository.findReadOnlyByUserName("박세헌1");
        for (Member member : m1) {
            member.setUserName("1");
        }
    }

    @Test
    public void CustomTest(){
        memberRepository.save(new Member("박세헌1", 16));
        memberRepository.save(new Member("박세헌2", 17));

        List<Member> m = memberRepository.findMemberCustom();
        for (Member member : m) {
            System.out.println(member);
        }
    }

    @Test
    public void projectionTest(){
        memberRepository.save(new Member("박세헌1", 16));
        memberRepository.save(new Member("박세헌2", 17));

        em.flush();
        em.clear();

        List<UserNameOnly> result = memberRepository.findProjectionByUserName("박세헌1", UserNameOnly.class);
        for (UserNameOnly userNameOnly : result) {
            System.out.println(userNameOnly.getUserName());
        }
    }

    @Test
    public void nativeProjectionTest(){
        Team team1 = new Team("광교");
        Team team2 = new Team("용인");
        teamRepository.save(team1);
        teamRepository.save(team2);

        memberRepository.save(new Member("박세헌1", 16, team1));
        memberRepository.save(new Member("박세헌2", 17, team2));

        em.flush();
        em.clear();

        Page<UserNameTeam> result = memberRepository.findNameTeamByUserName(PageRequest.of(0, 10));
        for (UserNameTeam m : result) {
            System.out.println(m.getUserName());
            System.out.println(m.getTeamName());
        }
    }
}