package com.example.itemservice.config;

import com.example.itemservice.repository.ItemRepository;
import com.example.itemservice.repository.jpa.JpaItemRepositoryV1;
import com.example.itemservice.repository.mybatis.ItemMapper;
import com.example.itemservice.repository.mybatis.MyBatisItemRepository;
import com.example.itemservice.service.ItemService;
import com.example.itemservice.service.ItemServiceImplV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {
    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceImplV1(itemRepository());
    }
    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV1(em);
    }
}
