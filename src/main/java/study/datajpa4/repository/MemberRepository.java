package study.datajpa4.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa4.dto.MemberDto;
import study.datajpa4.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername")
        // <- 이 쿼리 네임은 주석처리해도 된다. 이유는 제일 먼저 Member.findByUsername 이라는 네임드 쿼리가 있는지 찾기때문.
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa4.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username);

    Member findMemberByUsername(String username);

    Optional<Member> findOptionalByUsername(String username); //단건 Optional

    // (카운트쿼리 분리)
    // 조회용 쿼리는 조인하지만 카운트쿼리는 그냥 내보내면서 쓸때없이 count쿼리에 join이 일어나지 않게 한다.
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);

    List<Member> findListByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
//    @EntityGraph("Member.all") // NamedEntityGraph
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    //sql 힌트가 아니라 jpa 구현체에게 알려주는 힌트.
    //jpa 쿼리를 날릴때 하이버네이트에게 알려주는 힌트. jpa 구현체의 기능을 사용하고 싶을때 hint를 알려줘서 구현을 하는것.
    //이 상황은 하이버네이트에게 @QueryHint 로 readonly true 하겠다고 설정한 것
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    //비관적인 락 조회시 다른곳에서 손대지 못하게 락을 걸어준다. (돈을 맞출때 등등 사용, 트래픽 많은곳에서는 절대 사용X)
    //JPA가 LockModeType.PESSIMISTIC_WRITE 같은 락을 제공하고 spring data jpa가 이 락을 편하게 쓸 수 있게 @Lock를 제공한다.
    //select 쿼리가 나갈때 마지막에 for update 가 같이 붙어서 쿼리가 나가면서 락이 걸림
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
