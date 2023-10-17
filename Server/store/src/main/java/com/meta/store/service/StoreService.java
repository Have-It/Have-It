package com.meta.store.service;

import com.meta.store.client.CharacterServiceClient;
import com.meta.store.dto.request.RegistRequestDto;
import com.meta.store.dto.response.StoreResponseDto;
import com.meta.store.entity.Store;
import com.meta.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CharacterServiceClient characterServiceClient;

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getAllWithOwnership(Long memberId, String type) {
        List<Store> stores = storeRepository.findByType(type);
        // 보유 아이템 ID 리스트 가져오기.
        List<Long> ownedItemIds = characterServiceClient.getOwnedItemIdsByType(memberId, type);
        log.info(String.valueOf(ownedItemIds));
        return stores.stream()
                .map(store -> {
                    log.info(String.valueOf(ownedItemIds.contains(store.getTypeId())));
                    boolean owned = ownedItemIds.contains(store.getTypeId());
                    return new StoreResponseDto(store.getTypeId(), store.getPrice(), owned);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void registerItem(RegistRequestDto request) {
        Store store = new Store(request.getType(), request.getTypeId(), request.getPrice());
        storeRepository.save(store);
    }

}
