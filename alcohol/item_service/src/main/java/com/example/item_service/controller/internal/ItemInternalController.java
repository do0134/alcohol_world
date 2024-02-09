package com.example.item_service.controller.internal;

import com.example.common.response.Response;
import com.example.item_service.model.dto.OrderItem;
import com.example.item_service.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/item")
public class ItemInternalController {
    private final ItemService itemService;

    @GetMapping("/order/{itemId}")
    public Response<OrderItem> getOrderItem(@PathVariable("itemId") Long itemId) {
        OrderItem orderItem = itemService.getOrderItem(itemId);
        return Response.success(orderItem);
    }
}
