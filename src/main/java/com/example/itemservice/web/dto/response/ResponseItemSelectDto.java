package com.example.itemservice.web.dto.response;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class ResponseItemSelectDto {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
}
