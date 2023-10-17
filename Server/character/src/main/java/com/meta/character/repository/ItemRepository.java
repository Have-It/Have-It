package com.meta.character.repository;

import com.meta.character.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByMemberIdAndType(Long memberId, String type);
}
