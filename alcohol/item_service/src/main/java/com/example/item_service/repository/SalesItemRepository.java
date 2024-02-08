package com.example.item_service.repository;

import com.example.item_service.model.ItemType;
import com.example.item_service.model.entity.SalesItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesItemRepository extends JpaRepository<SalesItemEntity, Long> {
    Optional<SalesItemEntity> findByNameAndItemType(String name, ItemType itemType);
}
