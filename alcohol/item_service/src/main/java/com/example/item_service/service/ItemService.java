package com.example.item_service.service;


import com.example.item_service.model.ItemType;
import com.example.item_service.model.dto.Item;
import com.example.item_service.model.dto.OrderItem;
import com.example.item_service.model.dto.SalesItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;

public interface ItemService {
    void createItem(String name, String content, String image);
    Item getItem(Long itemId);

    SalesItem createSalesItem(Long itemId, ItemType itemType, Long price, Long stock, Timestamp startTime, Timestamp endTime);

    SalesItem getSalesItem(Long salesItemId);

    OrderItem getOrderItem(Long salesItemId);

    Page<SalesItem> getSalesItems(Pageable pageable, ItemType itemType);
    Long getStock(Long salesItemId);
    void updateStock(Long salesItemId);
}
