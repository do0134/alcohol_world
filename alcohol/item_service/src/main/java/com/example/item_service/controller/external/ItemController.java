package com.example.item_service.controller.external;


import com.example.common.response.Response;
import com.example.item_service.model.dto.Item;
import com.example.item_service.model.dto.SalesItem;
import com.example.item_service.model.dto.SalesItemRequest;
import com.example.item_service.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;
    @PostMapping("/")
    public Response<Void> createItem(@RequestBody Item item) {
        itemService.createItem(item.getName(), item.getContent(), item.getImage());
        return Response.success();
    }

    @GetMapping("/{itemId}")
    public Response<Item> getItem(@PathVariable("itemId") Long itemId) {
        Item item = itemService.getItem(itemId);
        return Response.success(item);
    }

    @PostMapping("/{itemId}")
    public Response<Void> createSalesItem(@PathVariable("itemId") Long itemId, @RequestBody SalesItemRequest salesItemRequest) {
        itemService.createSalesItem(itemId,salesItemRequest.getItemType(), salesItemRequest.getPrice(), salesItemRequest.getStock(),salesItemRequest.getStartTime(),salesItemRequest.getEndTime());
        return Response.success();
    }

    @GetMapping("/{salesItemId}")
    public Response<SalesItem> getSalesItem(@PathVariable("salesItemId") Long salesItemId) {
        SalesItem salesItem = itemService.getSalesItem(salesItemId);
        return Response.success(salesItem);
    }
}