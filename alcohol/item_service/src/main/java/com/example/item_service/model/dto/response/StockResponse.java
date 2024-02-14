package com.example.item_service.model.dto.response;

import lombok.Data;

@Data
public class StockResponse {
    private Long stock;

    public static StockResponse fromStock(Long stock) {
        StockResponse stockResponse = new StockResponse();
        stockResponse.setStock(stock);
        return stockResponse;
    }
}
