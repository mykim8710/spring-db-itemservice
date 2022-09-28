package com.example.itemservice.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 10)
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

    public Item() {} // JPA는 public 또는 protected 의 기본 생성자가 필수

}
