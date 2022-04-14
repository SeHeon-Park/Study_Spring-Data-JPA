package study.datajpa.datajpa.repository;

import study.datajpa.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
