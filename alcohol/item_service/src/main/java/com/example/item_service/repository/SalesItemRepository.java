package com.example.item_service.repository;

import com.example.item_service.model.ItemType;
import com.example.item_service.model.entity.SalesItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesItemRepository extends JpaRepository<SalesItemEntity, Long> {
    Optional<SalesItemEntity> findByItemNameAndItemType(String itemName, ItemType itemType);
    Page<SalesItemEntity> findAllByItemType(ItemType itemType, Pageable pageable);
}
