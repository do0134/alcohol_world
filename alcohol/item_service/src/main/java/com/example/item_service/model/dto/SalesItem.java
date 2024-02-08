package com.example.item_service.model.dto;

import com.example.item_service.model.ItemType;
import com.example.item_service.model.entity.ItemEntity;
import com.example.item_service.model.entity.SalesItemEntity;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SalesItem {
    private ItemEntity item;
    private ItemType itemType;
    private Long price;
    private Long stock;
    private Timestamp startTime;
    private Timestamp endTime;

    public static SalesItem fromEntity(SalesItemEntity sales) {
        SalesItem salesItem = new SalesItem();
        salesItem.setItem(sales.getItem());
        salesItem.setItemType(sales.getItemType());
        salesItem.setPrice(sales.getPrice());
        salesItem.setStock(sales.getStock());
        salesItem.setStartTime(sales.getStartTime());
        salesItem.setEndTime(sales.getEndTime());
        return salesItem;
    }
}
