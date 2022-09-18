package com.example.itemservice.service;

import com.example.itemservice.repository.ItemSearchCond;
import com.example.itemservice.repository.ItemUpdateDto;
import com.example.itemservice.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);

    void delete(Long itemId);
}
