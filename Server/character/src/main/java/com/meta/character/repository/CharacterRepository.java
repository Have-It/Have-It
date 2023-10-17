package com.meta.character.repository;

import com.meta.character.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<Character> findByMemberId(Long memberId);
    List<Character> findAllByMemberIdIn(List<Long> memberIds);
}
