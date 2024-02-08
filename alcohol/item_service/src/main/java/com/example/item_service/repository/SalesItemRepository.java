package com.example.item_service.repository;

import com.example.item_service.model.entity.SalesItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesItemRepository extends JpaRepository<SalesItemEntity, Long> {
}
