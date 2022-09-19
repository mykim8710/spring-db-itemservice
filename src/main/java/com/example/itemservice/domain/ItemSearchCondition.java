package com.example.itemservice.domain;

import com.example.itemservice.web.dto.request.RequestItemSelectDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemSearchCondition {
    private String itemName;
    private Integer maxPrice;

    @Builder
    public ItemSearchCondition(String itemName, Integer maxPrice) {
        this.itemName = itemName;
        this.maxPrice = maxPrice;
    }

    public static ItemSearchCondition makeItemSearchCondition(RequestItemSelectDto dto) {
        return ItemSearchCondition.builder()
                                    .itemName(dto.getItemName())
                                    .maxPrice(dto.getMaxPrice())
                                    .build();
    }
}
