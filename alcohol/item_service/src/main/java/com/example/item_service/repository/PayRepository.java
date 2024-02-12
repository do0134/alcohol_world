package com.example.item_service.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository{
    void update(Long userId, Long itemId);
}
