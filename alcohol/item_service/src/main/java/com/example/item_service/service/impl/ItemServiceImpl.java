package com.example.item_service.service.impl;

import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import com.example.item_service.model.dto.Item;
import com.example.item_service.model.entity.ItemEntity;
import com.example.item_service.repository.ItemRepository;
import com.example.item_service.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

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
}
