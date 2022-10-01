package com.example.itemservice.config;

import com.example.itemservice.repository.ItemRepository;
import com.example.itemservice.repository.jpa.JpaItemRepositoryV3;
import com.example.itemservice.repository.jpav2.ItemQuerydslRepositoryV2;
import com.example.itemservice.repository.jpav2.ItemRepositoryV2;
import com.example.itemservice.service.ItemService;
import com.example.itemservice.service.ItemServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class JpaV2Config {

    private final EntityManager em;
    private final ItemRepositoryV2 itemRepositoryV2;    // 자동으로 등록됨

    @Bean
    public ItemService itemService() {
        return new ItemServiceV2(itemRepositoryV2, ItemQuerydslRepositoryV2());
    }

    @Bean
    public ItemQuerydslRepositoryV2 ItemQuerydslRepositoryV2() {
        return new ItemQuerydslRepositoryV2(em);
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV3(em);
    }
}
