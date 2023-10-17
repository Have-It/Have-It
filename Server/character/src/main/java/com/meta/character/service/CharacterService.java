package com.meta.character.service;

import com.meta.character.client.DataServiceClient;
import com.meta.character.client.MissionServiceClient;
import com.meta.character.dto.request.*;
import com.meta.character.dto.response.*;
import com.meta.character.entity.Character;
import com.meta.character.entity.Item;
import com.meta.character.global.error.BusinessException;
import com.meta.character.global.error.ErrorCode;
import com.meta.character.repository.CharacterRepository;
import com.meta.character.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterService {

    private final ModelMapper modelMapper;
    private final CharacterRepository characterRepository;
    private final ItemRepository itemRepository;
    private final DataServiceClient dataServiceClient;
    private final MissionServiceClient missionServiceClient;

    @Transactional
    public Character createCharacter(Long memberId){
        Character character = Character
                .builder()
                .memberId(memberId)
                .build();
        return characterRepository.save(character);
    }

    @Transactional
    public CharacterInfoResponseDto getCharacter(Long memberId) {
        Optional<Character> optionalCharacter = characterRepository.findByMemberId(memberId);
        if (!optionalCharacter.isPresent()) {
            createCharacter(memberId);
            optionalCharacter = characterRepository.findByMemberId(memberId);
        }
        Character character = optionalCharacter.get();
        return CharacterInfoResponseDto.from(character);
    }

    @Transactional
    public CharacterInfoResponseDto updateCharacter(Long memberId, UpdateCharacterRequestDto request){
        Optional<Character> optionalCharacter = characterRepository.findByMemberId(memberId);
        if (!optionalCharacter.isPresent()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }
        Character character = optionalCharacter.get();

        modelMapper.map(request, character);
        Character updatedCharacter =  characterRepository.save(character);
        return CharacterInfoResponseDto.from(updatedCharacter);
    }

    @Transactional
    public List<Long> getItemTypeId(Long memberId, String type) {
        log.info("요청 서비스 들어옴"+memberId+type);
        return itemRepository.findByMemberIdAndType(memberId, type).stream()
                .map(Item::getTypeId)
                .collect(Collectors.toList());
    }

    @Transactional
    public BuyResponseDto buyItem(Long memberId, BuyRequestDto request) {
        ResponseEntity<BuyResponseDto> response = dataServiceClient.buyCoin(memberId, request.getPrice());
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new BusinessException(ErrorCode.NOT_ENOUGH_MONEY);
        }

        Item newItem = new Item();
        newItem.setMemberId(memberId);
        newItem.setType(request.getType());
        newItem.setTypeId(request.getTypeId());

        itemRepository.save(newItem);

        BuyResponseDto buyResponse = new BuyResponseDto();
        buyResponse.setRemainCoin(response.getBody().getRemainCoin());

        return buyResponse;
    }

    @Transactional
    public TopSuccessResultDto getTopSuccess() {
        ResponseEntity<TopSuccessRequestDto> responseEntity = dataServiceClient.rankSuccess();
        TopSuccessRequestDto result = responseEntity.getBody();

        List<Long> memberIds = result.getMemberIds();
        List<CharacterInfoResponseDto> characterInfosUnsorted = getCharacters(memberIds);
        
        Map<Long, CharacterInfoResponseDto> characterMap = new HashMap<>();
        for (CharacterInfoResponseDto info : characterInfosUnsorted) {
            characterMap.put(info.getMemberId(), info);
        }

        List<CharacterInfoResponseDto> characterInfosSorted = new ArrayList<>();

        for (Long memberId : memberIds) {
            characterInfosSorted.add(characterMap.get(memberId));
        }

        TopSuccessResultDto topSuccessResult = new TopSuccessResultDto();
        topSuccessResult.setMaxSuccessDay(result.getMaxSuccessDay());
        topSuccessResult.setNickNames(result.getNickNames());
        topSuccessResult.setCharacterInfos(characterInfosSorted);

        return topSuccessResult;
    }


    @Transactional
//    public synchronized List<CharacterInfoResponseDto> getCharacters(List<Long> memberIds) {
    public List<CharacterInfoResponseDto> getCharacters(List<Long> memberIds) {
        List<Character> characters = characterRepository.findAllByMemberIdIn(memberIds);

        Set<Long> foundMemberIds = characters.stream()
                .map(Character::getMemberId)
                .collect(Collectors.toSet());

        memberIds.stream()
                .filter(memberId -> !foundMemberIds.contains(memberId))
                .forEach(this::createCharacter);

        characters = characterRepository.findAllByMemberIdIn(memberIds);

        return characters.stream()
                .map(CharacterInfoResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public TopKcalResultDto getTopKcal() {
        ResponseEntity<TopKcalRequestDto> responseEntity = missionServiceClient.rankKcal();
        TopKcalRequestDto result = responseEntity.getBody();

        List<Long> memberIds = result.getMemberIds();
        List<CharacterInfoResponseDto> characterInfosUnsorted = getCharacters(memberIds);

        Map<Long, CharacterInfoResponseDto> characterMap = new HashMap<>();
        for (CharacterInfoResponseDto info : characterInfosUnsorted) {
            characterMap.put(info.getMemberId(), info);
        }

        List<CharacterInfoResponseDto> characterInfosSorted = new ArrayList<>();

        for (Long memberId : memberIds) {
            if(characterMap.containsKey(memberId)){
                characterInfosSorted.add(characterMap.get(memberId));
            } else {
                characterInfosSorted.add(null);
            }
        }

        TopKcalResultDto topKcalResult = new TopKcalResultDto();
        topKcalResult.setMaxKcal(result.getMaxKcal());
        topKcalResult.setNickNames(result.getNickNames());
        topKcalResult.setCharacterInfos(characterInfosSorted);

        return topKcalResult;
    }


    @Transactional
    public TopWalkResultDto getTopWalk() {
        ResponseEntity<TopWalkRequestDto> responseEntity = missionServiceClient.rankWalk();
        TopWalkRequestDto result = responseEntity.getBody();

        List<Long> memberIds = result.getMemberIds();
        List<CharacterInfoResponseDto> characterInfosUnsorted = getCharacters(memberIds);

        Map<Long, CharacterInfoResponseDto> characterMap = new HashMap<>();
        for (CharacterInfoResponseDto info : characterInfosUnsorted) {
            characterMap.put(info.getMemberId(), info);
        }

        List<CharacterInfoResponseDto> characterInfosSorted = new ArrayList<>();

        for (Long memberId : memberIds) {
            if(characterMap.containsKey(memberId)){
                characterInfosSorted.add(characterMap.get(memberId));
            } else {
                characterInfosSorted.add(null);
            }
        }

        TopWalkResultDto topWalkResult = new TopWalkResultDto();
        topWalkResult.setMaxWalk(result.getMaxWalk());
        topWalkResult.setNickNames(result.getNickNames());
        topWalkResult.setCharacterInfos(characterInfosSorted);

        return topWalkResult;
    }

}
