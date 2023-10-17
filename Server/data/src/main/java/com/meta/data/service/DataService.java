package com.meta.data.service;

import com.meta.data.dto.request.DataCreateRequestDto;
import com.meta.data.dto.response.BuyResponseDto;
import com.meta.data.dto.response.TopSuccessResultDto;
import com.meta.data.entity.Data;
import com.meta.data.global.error.BusinessException;
import com.meta.data.global.error.ErrorCode;
import com.meta.data.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataService {

    @Autowired
    private final DataRepository dataRepository;

    @Transactional
    public Data findMember(String key, String value) {
        if(key.equals("id")) {
            return dataRepository.findById(Long.valueOf(value)).orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }
    }

    @Transactional
    public Data setMission1Success(Data data) {
        if (!data.isMission1Success()) {
            data.setCoin(data.getCoin() + 10);
            data.setMission1Success(true);
            return dataRepository.save(data);
        } else {
            throw new BusinessException(ErrorCode.ALREADY_REQUEST);
        }
    }

    @Transactional
    public Data setMission2Success(Data data) {
        if (!data.isMission2Success()) {
            data.setCoin(data.getCoin() + 10);
            data.setMission2Success(true);
            return dataRepository.save(data);
        } else {
            throw new BusinessException(ErrorCode.ALREADY_REQUEST);
        }
    }

    @Transactional
    public Data setMission3Success(Data data) {
        if (!data.isMission3Success()) {
            data.setMission3Success(true);
            return dataRepository.save(data);
        } else {
            throw new BusinessException(ErrorCode.ALREADY_REQUEST);
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * ?")  // 매일 새벽 3시에 실행
//    @Scheduled(cron = "0 19 14 * * ?")
    public void resetAllMissions() {
        List<Data> allUsersData = dataRepository.findAll();
        for (Data data : allUsersData) {
            // mission1Success에 따른 characterWeight 값 조정
            if (data.isMission1Success()) {
                data.setCharacterWeight(Math.max(0, data.getCharacterWeight() - 1));
            } else {
                data.setCharacterWeight(Math.min(24, data.getCharacterWeight() + 1));
            }

            if (data.getCharacterWeight() <= 4) {
                data.setEffectWeight(1);
            }

            // mission2Success에 따른 characterDarkcircle 값 조정
            if (data.isMission2Success()) {
                data.setCharacterDarkcircle(Math.max(0, data.getCharacterDarkcircle() - 1));
            } else {
                data.setCharacterDarkcircle(Math.min(24, data.getCharacterDarkcircle() + 1));
            }

            if (data.getCharacterDarkcircle() >= 20) {
                data.setEffectDarkcircle(2);
            } else if (data.getCharacterDarkcircle() <= 4) {
                data.setEffectDarkcircle(1);
            }

            // mission3Success에 따른 dailyPet 값 설정
            if (data.isMission3Success()) {
                data.setDailyPet(true);
            } else {
                data.setDailyPet(false);
            }

            // 세 개의 success 항목이 모두 true라면 totalSuccess 값을 증가시킴
            if(data.isMission1Success() &&
                    data.isMission2Success() &&
                    data.isMission3Success()){
                data.setTotalSuccess(data.getTotalSuccess()+1);
            }

            // 미션 success 상태 초기화
            data.setMission1Success(false);
            data.setMission2Success(false);
            data.setMission3Success(false);

        }
        dataRepository.saveAll(allUsersData);
    }

    @Transactional
    public void createData(DataCreateRequestDto request) {
        Data newData = Data.builder()
                .memberId(request.getMemberId())
                .nickName(request.getNickName())
                .effectWeight(request.getEffectWeight())
                .effectDarkcircle(request.getEffectDarkcircle())
                .totalSuccess(request.getTotalSuccess())
                .coin(request.getCoin())
                .characterWeight(request.getCharacterWeight())
                .characterDarkcircle(request.getCharacterDarkcircle())
                .mission1Success(request.isMission1Success())
                .mission2Success(request.isMission2Success())
                .mission3Success(request.isMission3Success()).build();

        Data savedData = dataRepository.save(newData);
    }

    @Transactional(readOnly = true)
    public TopSuccessResultDto getTopSuccess() {
        List<Data> topMissions = dataRepository.findAllByOrderByTotalSuccessDesc();

        if (!topMissions.isEmpty()) {
            int maxSuccess = topMissions.get(0).getTotalSuccess();
            List<String> nickNames = topMissions.stream()
                    .filter(m -> m.getTotalSuccess() == maxSuccess)
                    .map(m -> m.getNickName())
                    .distinct()
                    .collect(Collectors.toList());
            List<Long> memberIds = topMissions.stream()
                    .filter(m -> m.getTotalSuccess() == maxSuccess)
                    .map(m -> m.getId())
                    .distinct()
                    .collect(Collectors.toList());
            TopSuccessResultDto result = new TopSuccessResultDto();
            result.setMaxSuccessDay(maxSuccess);
            result.setNickNames(nickNames);
            result.setMemberIds(memberIds);

            return result;
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_NO1MEMBER);
        }
    }

    @Transactional
    public BuyResponseDto buyCoin(Long memberId, int price) {

        Data data = dataRepository.findById(memberId).get();


        if (data.getCoin() < price) {
            throw new BusinessException(ErrorCode.NOT_ENOUGH_MONEY);
        }

        int remainCoin = data.getCoin() - price;
        data.setCoin(remainCoin);
        dataRepository.save(data);

        return new BuyResponseDto(remainCoin);
    }

    @Transactional
    public void update(Long id, String nickName) {
        Optional<Data> optionalData = dataRepository.findById(id);
        if (optionalData.isPresent()) {
            Data data = optionalData.get();
            data.setNickName(nickName);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }
    }

    @Transactional
    public String testDay(Data userData, int number){
        int newTotalSuccess = userData.getTotalSuccess() + number;
        userData.setTotalSuccess(newTotalSuccess);
        dataRepository.save(userData);
        return "성공!";
    }
}
