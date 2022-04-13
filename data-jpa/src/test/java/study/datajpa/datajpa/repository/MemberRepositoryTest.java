package study.datajpa.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.datajpa.entity.Member;
import study.datajpa.datajpa.entity.Team;

import javax.validation.constraints.AssertFalse;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    TeamRepository teamRepository;

    @Test
    public void MemberRepositoryTest(){

        Member m1 = new Member("박세헌", 24);
        memberRepository.save(m1);

        Member m2 = new Member("박세헌", 27);
        memberRepository.save(m2);

        List<Member> findMember = memberRepository.findByUserNameAndAgeGreaterThan("박세헌", 25);
        assertThat(findMember.get(0).getAge()).isGreaterThan(24);

        List<Member> findMember2 = memberRepository.findUser("박세헌", 24);
        assertThat(findMember2.get(0)).isEqualTo(m1);
    }

}