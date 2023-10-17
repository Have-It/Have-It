package com.meta.mission.repository;

import com.meta.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    Optional<Mission> findByMemberId(Long memberId);
    List<Mission> findByRecordDateOrderByTotalKcalDesc(Date recordDate);
    List<Mission> findByRecordDateOrderByTotalWalkDesc(Date recordDate);
    Mission findByMemberIdAndRecordDate(Long memberId, Date recordDate);
}
