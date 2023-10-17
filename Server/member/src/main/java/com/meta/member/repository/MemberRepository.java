package com.meta.member.repository;

import com.meta.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
    Optional<Member> findById(Long memberId);
    Optional<Member> findByEmail(String email);

}
