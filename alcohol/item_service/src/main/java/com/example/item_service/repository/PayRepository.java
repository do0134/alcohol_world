package com.example.item_service.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository{
    void update(Long userId, Long itemId);
}
