package com.example.itemservice.config;

import com.example.itemservice.repository.ItemRepository;
import com.example.itemservice.repository.mybatis.ItemMapper;
import com.example.itemservice.repository.mybatis.MyBatisItemRepository;
import com.example.itemservice.service.ItemService;
import com.example.itemservice.service.ItemServiceImplV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {
    private final ItemMapper itemMapper;

    @Bean
    public ItemService itemService() {
        return new ItemServiceImplV1(itemRepository());
    }
    @Bean
    public ItemRepository itemRepository() {
        return new MyBatisItemRepository(itemMapper);
    }
}
