package com.example.item_service.model.dto.request;

import com.example.item_service.model.ItemType;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SalesItemRequest {
    private ItemType itemType;
    private Long price;
    private Long stock;
    private Timestamp startTime;
    private Timestamp endTime;
}
