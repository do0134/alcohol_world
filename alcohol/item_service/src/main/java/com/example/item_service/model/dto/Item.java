package com.example.item_service.model.dto;

import com.example.item_service.model.entity.ItemEntity;
import lombok.Data;

@Data
public class Item {
    private String name;
    private String content;
    private String image;

    public static Item fromEntity(ItemEntity itemEntity) {
        Item item = new Item();
        item.setName(itemEntity.getName());
        item.setContent(itemEntity.getContent());
        item.setImage(itemEntity.getImage());
        return item;
    }

}
