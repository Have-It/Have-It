package com.meta.game.repository;

import com.meta.game.entity.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<GameResult, Long> {
    Optional<GameResult> findByMemberId(Long memberId);
    List<GameResult> findAllByOrderByWinCountDesc();
}
