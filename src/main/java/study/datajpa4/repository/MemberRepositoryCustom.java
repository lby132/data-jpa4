package study.datajpa4.repository;

import org.springframework.stereotype.Repository;
import study.datajpa4.entity.Member;

import java.util.List;

@Repository
public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();
}
