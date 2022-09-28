package com.example.itemservice.repository;

import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Item save(Item item);

    void update(Item item);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCondition condition);

    void delete(Long itemId);

}
