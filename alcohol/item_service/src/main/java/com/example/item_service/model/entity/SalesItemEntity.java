package com.example.item_service.model.entity;

import com.example.item_service.model.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "SalesItem")
@Getter
@Setter
public class SalesItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item")
    private ItemEntity item;

    @Column(name = "item_type")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name = "price")
    private Long price;

    @Column(name = "stock")
    private Long stock;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static SalesItemEntity toEntity(ItemEntity item, ItemType itemType, Long price, Long stock,
                                           Timestamp startTime, Timestamp endTime) {
        SalesItemEntity salesItemEntity = new SalesItemEntity();
        salesItemEntity.setItem(item);
        salesItemEntity.setItemType(itemType);
        salesItemEntity.setPrice(price);
        salesItemEntity.setStock(stock);
        salesItemEntity.setStartTime(startTime);
        salesItemEntity.setEndTime(endTime);
        return salesItemEntity;
    }
}
