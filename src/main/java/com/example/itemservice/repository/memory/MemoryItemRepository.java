package com.example.itemservice.repository.memory;

import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import com.example.itemservice.repository.ItemRepository;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

//@Repository
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    @Override
    public void update(Item updateItem) {
        Item findItem = findById(updateItem.getId()).orElseThrow();
        findItem.setItemName(updateItem.getItemName());
        findItem.setPrice(updateItem.getPrice());
        findItem.setQuantity(updateItem.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Item> findAll(ItemSearchCondition condition) {
        System.out.println("condition = " + condition);

        String itemName = condition.getItemName();
        Integer maxPrice = condition.getMaxPrice();
        return store.values().stream()
                .filter(item -> {
                    if (ObjectUtils.isEmpty(itemName)) {
                        return true;
                    }
                    return item.getItemName().contains(itemName);
                }).filter(item -> {
                    if (maxPrice == null) {
                        return true;
                    }
                    return item.getPrice() <= maxPrice;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long itemId) {
        store.remove(itemId);
    }

    public void clearStore() {
        store.clear();
    }

}
