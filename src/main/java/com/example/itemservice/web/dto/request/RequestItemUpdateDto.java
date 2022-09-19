package com.example.itemservice.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestItemUpdateDto {
    private String itemName;
    private Integer price;
    private Integer quantity;

    public RequestItemUpdateDto(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
