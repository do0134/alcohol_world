package com.example.item_service.service;


import com.example.item_service.model.ItemType;
import com.example.item_service.model.dto.Item;
import com.example.item_service.model.dto.SalesItem;

import java.sql.Timestamp;

public interface ItemService {
    void createItem(String name, String content, String image);
    Item getItem(Long itemId);

    void createSalesItem(Long itemId, ItemType itemType, Long price, Long stock, Timestamp startTime, Timestamp endTime);

    SalesItem getSalesItem(Long salesItemId);
}
