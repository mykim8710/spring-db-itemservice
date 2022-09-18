package com.example.itemservice.config;

import com.example.itemservice.repository.ItemRepository;
import com.example.itemservice.repository.memory.MemoryItemRepository;
import com.example.itemservice.service.ItemService;
import com.example.itemservice.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }

}
