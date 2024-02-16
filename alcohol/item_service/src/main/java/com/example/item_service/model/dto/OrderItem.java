package com.example.item_service.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class OrderItem {
    private String name;
    private Long price;
    private Timestamp start_time;
    private Timestamp end_time;
}
