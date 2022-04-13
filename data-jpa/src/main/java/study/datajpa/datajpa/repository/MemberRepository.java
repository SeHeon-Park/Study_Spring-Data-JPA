package study.datajpa.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.datajpa.entity.Member;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUserNameAndAgeGreaterThan(String userName, int age); // Query method

    @Query("select m from Member m where m.userName = :userName and m.age = :age") // Query애 바로 넣기
    List<Member> findUser(@Param("userName") String userName, @Param("age") int age);
}
