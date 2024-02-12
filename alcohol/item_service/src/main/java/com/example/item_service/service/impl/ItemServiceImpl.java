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
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final SalesItemRepository salesItemRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final EntityManager entityManager;

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
    public void createSalesItem(Long itemId, ItemType itemType, Long price, Long stock, Timestamp startTime, Timestamp endTime) {
        ItemEntity item = getItemEntity(itemId);
        Optional<SalesItemEntity> salesItem = salesItemRepository.findByItemNameAndItemType(item.getName(), itemType);

        if (salesItem.isPresent()) {
            throw new AlcoholException(ErrorCode.ITEM_ALREADY_EXIST, "존재하는 판매상품입니다.");
        }

        salesItemRepository.save(SalesItemEntity.toEntity(item,itemType,price,stock,startTime,endTime));
//        publishSalesItemQuantity(itemId, stock);
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

    @Override
    public Page<SalesItem>  getSalesItems(Pageable pageable, ItemType itemType) {
        Page<SalesItemEntity> reservationItems = salesItemRepository.findAllByItemType(itemType, pageable);
        return reservationItems.map(SalesItem::fromEntity);
    }

    @Override
    @Transactional
    public synchronized void pay(Long userId, Long itemId) {
//        SalesItemEntity item = salesItemRepository.findById(itemId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ITEM));
//
//        if (item.getStock() < 1) {
//            throw new AlcoholException(ErrorCode.NO_SUCH_ITEM, String.format("%s님의 주문이 실패했습니다. 재고가 부족합니다.", userId));
//        }

        SalesItemEntity item = entityManager.find(SalesItemEntity.class, itemId);

        if (item.getStock() < 1) {
            throw new AlcoholException(ErrorCode.NO_SUCH_ITEM, String.format("%s님의 주문이 실패했습니다. 재고가 부족합니다.", userId));
        }

        item.setStock(item.getStock()-1);
        entityManager.merge(item);
    }

    public ItemEntity getItemEntity(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ITEM));
    }

    public void publishSalesItemQuantity(Long itemId, Long stock){
        ObjectRecord<String, Long> record = StreamRecords.newRecord()
                .ofObject(stock)
                .withStreamKey(getRedisKey(itemId));
        redisTemplate.opsForStream().add(record);
        log.info(String.format(String.format("Stream for item %s has started. Initial inventory is %d.", itemId, stock)));
    }

    public String getRedisKey(Long itemId) {
        String redisKey = "SalesItem";
        return redisKey + " " + itemId;
    }
}
