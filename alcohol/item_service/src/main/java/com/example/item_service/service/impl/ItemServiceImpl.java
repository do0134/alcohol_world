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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
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
    @Transactional
    public SalesItem createSalesItem(Long itemId, ItemType itemType, Long price, Long stock, Timestamp startTime, Timestamp endTime) {
        ItemEntity item = getItemEntity(itemId);
        Optional<SalesItemEntity> salesItem = salesItemRepository.findByItemNameAndItemType(item.getName(), itemType);

        if (salesItem.isPresent()) {
            throw new AlcoholException(ErrorCode.ITEM_ALREADY_EXIST, "존재하는 판매상품입니다.");
        }

        SalesItemEntity salesItemEntity = salesItemRepository.save(SalesItemEntity.toEntity(item,itemType,price,stock,startTime,endTime));
        return SalesItem.fromEntity(salesItemEntity);
    }

    @Override
    public SalesItem getSalesItem(Long salesItemId) {
        SalesItemEntity salesItemEntity = getSalesItemEntity(salesItemId);
        return SalesItem.fromEntity(salesItemEntity);
    }

    @Override
    public OrderItem getOrderItem(Long salesItemId) {
        SalesItemEntity salesItemEntity = getSalesItemEntity(salesItemId);
        OrderItem orderItem = new OrderItem();
        orderItem.setName(salesItemEntity.getItem().getName());
        orderItem.setPrice(salesItemEntity.getPrice());
        orderItem.setStart_time(salesItemEntity.getStartTime());
        orderItem.setEnd_time(salesItemEntity.getEndTime());

        return orderItem;
    }

    @Override
    public Page<SalesItem>  getSalesItems(Pageable pageable, ItemType itemType) {
        Page<SalesItemEntity> reservationItems = salesItemRepository.findAllByItemType(itemType, pageable);
        return reservationItems.map(SalesItem::fromEntity);
    }

    @Override
    public Long getStock(Long salesItemId) {
        SalesItemEntity salesItem = getSalesItemEntity(salesItemId);
        return salesItem.getStock();
    }

    @Override
    @Transactional
    public void updateStock(Long salesItemId) {
        SalesItemEntity salesItemEntity = salesItemRepository.findByIdWithPessimisticReadLock(salesItemId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ITEM));
        salesItemEntity.setStock(salesItemEntity.getStock()-1);
        salesItemRepository.save(salesItemEntity);
    }


    private ItemEntity getItemEntity(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ITEM));
    }

    private SalesItemEntity getSalesItemEntity(Long salesItemId) {
        return salesItemRepository.findById(salesItemId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ITEM));
    }
}
