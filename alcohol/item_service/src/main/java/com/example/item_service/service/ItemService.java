package com.example.item_service.service;


import com.example.item_service.model.dto.Item;

public interface ItemService {
    void createItem(String name, String content, String image);
    Item getItem(Long itemId);
}
