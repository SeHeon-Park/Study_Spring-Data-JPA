package study.datajpa.datajpa.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.AssertTrue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void entityTest(){
        Member member = new Member();
        Team team = new Team("두산");
        em.persist(team);

        member.setUserName("박세헌");
        member.changeTeam(team);
        em.persist(member);

        //초기화
        em.flush();
        em.clear();

        //확인
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        for (Member member1 : result) {
            System.out.println(member1);
            System.out.println(member1.getTeam());
        }

    }
}