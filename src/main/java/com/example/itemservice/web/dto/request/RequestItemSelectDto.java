package com.example.itemservice.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestItemSelectDto {
    private String itemName;
    private Integer maxPrice;
}
