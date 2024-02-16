package com.example.item_service.repository;

import com.example.item_service.model.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByName(String name);
}
