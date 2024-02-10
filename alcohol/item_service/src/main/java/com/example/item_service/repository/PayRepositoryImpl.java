package com.example.item_service.repository;

import com.example.item_service.model.entity.SalesItemEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class PayRepositoryImpl implements PayRepository{
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void update(Long userId, Long itemId) {
        SalesItemEntity item = entityManager.find(SalesItemEntity.class, itemId);
        item.setStock(item.getStock()-1);
        entityManager.merge(item);
        entityManager.clear();
        entityManager.flush();
    }
}
