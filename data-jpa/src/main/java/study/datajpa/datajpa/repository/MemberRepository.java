package study.datajpa.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.datajpa.dto.MemberDto;
import study.datajpa.datajpa.entity.Member;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUserNameAndAgeGreaterThan(String userName, int age); // Query method

    @Query("select m from Member m where m.userName = :userName and m.age = :age") // Query에 바로 넣기
    List<Member> findUser(@Param("userName") String userName, @Param("age") int age);

    @Query("select new study.datajpa.datajpa.dto.MemberDto(m.id, m.userName, t.name) " +  //Dto로 조회
            "from Member m join m.team t")
    List<MemberDto> findDto();

    @Query("select m from Member m where m.userName in :name")   // 파라미터 list 형식으로 받기(in)
    List<Member> findMemberByNames(@Param("name") Collection<String> name);

}
