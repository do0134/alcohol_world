package com.example.item_service.repository;

import com.example.item_service.model.ItemType;
import com.example.item_service.model.entity.SalesItemEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesItemRepository extends JpaRepository<SalesItemEntity, Long> {
    Optional<SalesItemEntity> findByItemNameAndItemType(String itemName, ItemType itemType);
    Page<SalesItemEntity> findAllByItemType(ItemType itemType, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT salesItem FROM SalesItemEntity salesItem WHERE salesItem.id = :salesItemId")
    Optional<SalesItemEntity> findByIdWithPessimisticReadLock(@Param("salesItemId") Long salesItemId);
}
