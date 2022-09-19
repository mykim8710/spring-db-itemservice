package com.example.itemservice.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RequestItemInsertDto {
    private String  itemName;
    private Integer price;
    private Integer quantity;
}
