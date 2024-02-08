package com.example.item_service.service.impl;

import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import com.example.item_service.model.ItemType;
import com.example.item_service.model.dto.Item;
import com.example.item_service.model.dto.SalesItem;
import com.example.item_service.model.entity.ItemEntity;
import com.example.item_service.model.entity.SalesItemEntity;
import com.example.item_service.repository.ItemRepository;
import com.example.item_service.repository.SalesItemRepository;
import com.example.item_service.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final SalesItemRepository salesItemRepository;

    @Override
    @Transactional
    public void createItem(String name, String content, String image) {
        Optional<ItemEntity> item = itemRepository.findByName(name);
        if (item.isPresent()) {
            throw new AlcoholException(ErrorCode.ITEM_ALREADY_EXIST);
        }

        itemRepository.save(ItemEntity.toEntity(name, content, image));
    }

    @Override
    public Item getItem(Long itemId) {
        ItemEntity item = itemRepository.findById(itemId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ITEM));

        return Item.fromEntity(item);
    }

    @Override
    public void createSalesItem(Long itemId, ItemType itemType, Long price, Long stock, Timestamp startTime, Timestamp endTime) {
        Item item = getItem(itemId);
        Optional<SalesItemEntity> salesItem = salesItemRepository.findByNameAndItemType(item.getName(), itemType);

        if (salesItem.isPresent()) {
            throw new AlcoholException(ErrorCode.ITEM_ALREADY_EXIST);
        }

        salesItemRepository.save(SalesItemEntity.toEntity(ItemEntity.toEntity(item.getName(), item.getContent(),item.getImage()),
                itemType,price,stock,startTime,endTime)
        );
    }

    @Override
    public SalesItem getSalesItem(Long salesItemId) {
        SalesItemEntity salesItemEntity = salesItemRepository.findById(salesItemId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ITEM));

        return SalesItem.fromEntity(salesItemEntity);
    }
}
