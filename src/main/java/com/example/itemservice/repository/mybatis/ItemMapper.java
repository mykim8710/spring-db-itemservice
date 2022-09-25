package com.example.itemservice.repository.mybatis;

import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {
    void save(Item item);
    void update(Item item);
    Optional<Item> findById(Long id);
    List<Item> findAll(ItemSearchCondition condition);
    void delete(Long itemId);
}
