package com.meta.game.service;

import com.meta.game.client.MemberServiceClient;
import com.meta.game.dto.response.GameRankDto;
import com.meta.game.dto.response.GameResultResponseDto;
import com.meta.game.entity.GameResult;
import com.meta.game.global.error.BusinessException;
import com.meta.game.global.error.ErrorCode;
import com.meta.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final MemberServiceClient memberServiceClient;

    @Transactional
    public GameResult createGameResult(Long memberId){
        String nickName = memberServiceClient.getNickname(memberId);
        GameResult gameResult = GameResult
            .builder()
            .memberId(memberId)
            .nickName(nickName)
            .build();
        return gameRepository.save(gameResult);
    }

    @Transactional
    public GameResultResponseDto updateResult(Long memberId) {
        Optional<GameResult> optionalGameResult = gameRepository.findByMemberId(memberId);
        if (!optionalGameResult.isPresent()) {
            createGameResult(memberId);
            optionalGameResult = gameRepository.findByMemberId(memberId);
        }

        GameResult gameResult = optionalGameResult.get();
        int updatedWinCount = gameResult.getWinCount() + 1;
        gameResult.setWinCount(updatedWinCount);

        return new GameResultResponseDto(gameResult);
    }

    @Transactional
    public void update(Long memberId, String nickName) {
        Optional<GameResult> optionalMission = gameRepository.findByMemberId(memberId);
        if (optionalMission.isPresent()) {
            GameResult gameResult = optionalMission.get();
            gameResult.setNickName(nickName);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }
    }

    @Transactional
    public List<GameRankDto> getTop() {
        return gameRepository.findAllByOrderByWinCountDesc().stream()
                .map(game -> new GameRankDto(game.getNickName(), game.getWinCount()))
                .collect(Collectors.toList());
    }

}
