package com.example.itemservice.repository.mybatis;

import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import com.example.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MyBatisItemRepository implements ItemRepository {
    private final ItemMapper itemMapper;

    @Override
    public Item save(Item item) {
        log.info("itemMapper class={}", itemMapper.getClass());
        itemMapper.save(item);
        return item;
    }

    @Override
    public void update(Item item) {
        itemMapper.update(item);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemMapper.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCondition condition) {
        return itemMapper.findAll(condition);
    }

    @Override
    public void delete(Long itemId) {
        itemMapper.delete(itemId);
    }
}
