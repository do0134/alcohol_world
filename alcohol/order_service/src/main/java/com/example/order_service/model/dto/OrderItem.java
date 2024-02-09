package com.example.order_service.model.dto;

import lombok.Data;

@Data
public class OrderItem {
    private String name;
    private Long price;
}
