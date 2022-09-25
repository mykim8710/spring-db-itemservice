package com.example.itemservice.domain;

import com.example.itemservice.web.dto.request.RequestItemInsertDto;
import com.example.itemservice.web.dto.request.RequestItemUpdateDto;
import com.example.itemservice.web.dto.response.ResponseItemSelectDto;
import lombok.*;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    @Builder
    public Item(Long id, String itemName, Integer price, Integer quantity) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public static Item makeSaveModel(RequestItemInsertDto dto) {
        return Item.builder()
                        .itemName(dto.getItemName())
                        .price(dto.getPrice())
                        .quantity(dto.getQuantity())
                        .build();

    }

    public static Item makeUpdateModel(Long itemId, RequestItemUpdateDto dto) {
        return Item.builder()
                        .id(itemId)
                        .itemName(dto.getItemName())
                        .price(dto.getPrice())
                        .quantity(dto.getQuantity())
                        .build();
    }

    public ResponseItemSelectDto toResponseItemSelectDto() {
        return ResponseItemSelectDto.builder()
                                        .id(id)
                                        .itemName(itemName)
                                        .price(price)
                                        .quantity(quantity)
                                        .build();
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
