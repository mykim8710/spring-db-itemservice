package com.example.itemservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ItemSearchCondition {
    private String itemName;
    private Integer maxPrice;

    public ItemSearchCondition(String itemName, Integer maxPrice) {
        this.itemName = itemName;
        this.maxPrice = maxPrice;
    }
}
