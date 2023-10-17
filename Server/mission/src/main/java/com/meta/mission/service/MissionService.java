package com.meta.mission.service;

import com.meta.mission.dto.request.MissionCreateRequestDto;

import com.meta.mission.dto.request.RankRecordRequestDto;

import com.meta.mission.dto.response.TopKcalResultDto;
import com.meta.mission.dto.response.TopWalkResultDto;
import com.meta.mission.entity.Mission;
import com.meta.mission.global.error.BusinessException;
import com.meta.mission.global.error.ErrorCode;
import com.meta.mission.repository.MissionRepository;
import lombok.Builder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

//    @Transactional(readOnly = true)
//    public Mission findMember(String key, String value) {
//
//        if(key.equals("id")) {
//            return missionRepository.findByMemberId(Long.valueOf(value)).orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
//        } else {
//            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
//        }
//    }

//    @Transactional
//    public Mission record(Mission mission, MissionRecordRequestDto request){
//        mission.builder()
//                .sleepTime(request.getSleepTime())
//                .customMissionType(request.getCustomMissionType())
//                .customMissionDescript(request.getCustomMissionDescript())
//                .build();
//        missionRepository.save(mission);
//        return mission;
//    }


    @Transactional
    public Mission rankRecord(Long memberId, RankRecordRequestDto request){
        LocalDate today = LocalDate.now();
        Date now = java.sql.Date.valueOf(today);

        Mission mission = missionRepository.findByMemberIdAndRecordDate(memberId, now);

        if (mission == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_TODAY);
        }

        mission.setTotalKcal(request.getTotalKcal());
        mission.setTotalWalk(request.getTotalWalk());

        missionRepository.save(mission);

        return mission;
    }

    @Transactional
    public TopKcalResultDto getTopKcal() {
        LocalDate today = LocalDate.now();
        Date recordDate = java.sql.Date.valueOf(today);

        List<Mission> topMissions = missionRepository.findByRecordDateOrderByTotalKcalDesc(recordDate);

        if (!topMissions.isEmpty()) {
            int maxKcal = topMissions.get(0).getTotalKcal();

            List<String> nickNames = topMissions.stream()
                    .filter(m -> m.getTotalKcal() == maxKcal)
                    .map(Mission::getNickName)
                    .distinct()
                    .collect(Collectors.toList());

            List<Long> memberIds = topMissions.stream()
                    .filter(m -> m.getTotalKcal() == maxKcal)
                    .map(Mission::getMemberId)
                    .distinct()
                    .collect(Collectors.toList());

            TopKcalResultDto result = new TopKcalResultDto();
            result.setMaxKcal(maxKcal);
            result.setMemberIds(memberIds);
            result.setNickNames(nickNames);

            return result;

        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_NO1MEMBER);
        }
    }

    @Transactional(readOnly = true)
    public TopWalkResultDto getTopWalk() {
        LocalDate today = LocalDate.now();
        Date recordDate = java.sql.Date.valueOf(today);


        List<Mission> topMissions = missionRepository.findByRecordDateOrderByTotalWalkDesc(recordDate);


        if (!topMissions.isEmpty()) {
            int maxWalk = topMissions.get(0).getTotalWalk();

            List<String> nickNames = topMissions.stream()
                    .filter(m -> m.getTotalWalk() == maxWalk)
                    .map(m -> m.getNickName())
                    .distinct()
                    .collect(Collectors.toList());
            List<Long> memberIds = topMissions.stream()
                    .filter(m -> m.getTotalWalk() == maxWalk)
                    .map(m -> m.getMemberId())
                    .distinct()
                    .collect(Collectors.toList());
            TopWalkResultDto result = new TopWalkResultDto();
            result.setMaxWalk(maxWalk);
            result.setMemberIds(memberIds);
            result.setNickNames(nickNames);

            return result;

        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_NO1MEMBER);
        }
    }

    @Transactional
    public void createMission(MissionCreateRequestDto request) {
        Mission newMission = Mission.builder()
                .memberId(request.getMemberId())
                .recordDate(request.getRecordDate())
                .nickName(request.getNickName())
                .totalKcal(request.getTotalKcal())
                .totalWalk(request.getTotalWalk())
                .build();

        Mission savedMission = missionRepository.save(newMission);
    }

    @Transactional
    public void update(Long memberId, String nickName) {
        Optional<Mission> optionalMission = missionRepository.findByMemberId(memberId);
        if (optionalMission.isPresent()) {
            Mission mission = optionalMission.get();
            mission.setNickName(nickName);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }
    }

    //    @Scheduled(cron = "0 5 4 * * ?")
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행 (모바일 구현되기전까지만 사용)
    @Transactional
    public void createMissionForNewDay() {
        List<Mission> missions = missionRepository.findAll(); // 모든 미션 정보 가져오기

        for (Mission mission : missions) {
            MissionCreateRequestDto request = new MissionCreateRequestDto();
            request.setMemberId(mission.getMemberId());
            request.setNickName(mission.getNickName());

            request.setTotalKcal(0);
            request.setTotalWalk(0);

            LocalDate today = LocalDate.now();
            Date now = java.sql.Date.valueOf(today);
            request.setRecordDate(now);

            createMission(request);
        }
    }
}
