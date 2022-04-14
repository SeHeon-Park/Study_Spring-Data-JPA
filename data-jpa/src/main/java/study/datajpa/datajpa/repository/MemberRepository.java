package study.datajpa.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.datajpa.dto.MemberDto;
import study.datajpa.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    List<Member> findCollectionByUserName(String userName); // 컬렉션
    Member findListByUserName(String serName); // 단건
    Optional<Member> findOptionalByUserName(String userName); // 단건 optional

//    @Query(value = "select m from Member m join m.team t",
//            countQuery = "select count(m) from Member m")  // count 따로 뺴기(상황에 따라 성능 향상)
    Page<Member> findPageByAge(int age, Pageable pageable);

    // @Modifying 써줘야함
    @Modifying(clearAutomatically = true) // bulk연산은 한방에 db에 때리기 때문에 영속성 context clear시켜줘야함
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    @Query("select m from Member m join fetch m.team")
    List<Member> findFetchMember();

    @EntityGraph(attributePaths = {"team"}) // fetch join한 것과 똑같은 코드
    @Override
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"}) // fetch join한 것과 똑같은 코드
    @Query("select m from Member m")
    List<Member> findEntityGraphMember();

    // readOnly조건 걸어줌(성능상 확실히 향상될 때만 쓰자, 무차별 x)
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Member> findReadOnlyByUserName(String userName);

    // 찾아보기, 가급적 쓰지 말길
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUserName(String userName);
}
