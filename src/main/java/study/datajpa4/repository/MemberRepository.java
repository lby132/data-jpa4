package study.datajpa4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa4.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
