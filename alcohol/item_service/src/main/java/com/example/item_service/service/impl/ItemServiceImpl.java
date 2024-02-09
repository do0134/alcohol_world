package com.example.item_service.service.impl;

import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import com.example.item_service.model.ItemType;
import com.example.item_service.model.dto.Item;
import com.example.item_service.model.dto.OrderItem;
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
        ItemEntity item = getItemEntity(itemId);

        return Item.fromEntity(item);
    }

    @Override
    public void createSalesItem(Long itemId, ItemType itemType, Long price, Long stock, Timestamp startTime, Timestamp endTime) {
        ItemEntity item = getItemEntity(itemId);
        Optional<SalesItemEntity> salesItem = salesItemRepository.findByItemNameAndItemType(item.getName(), itemType);

        if (salesItem.isPresent()) {
            throw new AlcoholException(ErrorCode.ITEM_ALREADY_EXIST, "존재하는 판매상품입니다.");
        }

        salesItemRepository.save(SalesItemEntity.toEntity(item,itemType,price,stock,startTime,endTime)
        );
    }

    @Override
    public SalesItem getSalesItem(Long salesItemId) {
        SalesItemEntity salesItemEntity = salesItemRepository.findById(salesItemId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ITEM));

        return SalesItem.fromEntity(salesItemEntity);
    }

    @Override
    public OrderItem getOrderItem(Long salesItemId) {
        SalesItemEntity salesItemEntity = salesItemRepository.findById(salesItemId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ITEM));
        OrderItem orderItem = new OrderItem();
        orderItem.setName(salesItemEntity.getItem().getName());
        orderItem.setPrice(salesItemEntity.getPrice());
        return orderItem;
    }

    public ItemEntity getItemEntity(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ITEM));
    }
}
