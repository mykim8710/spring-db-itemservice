package com.example.itemservice.repository.jpav2;

import com.example.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryV2 extends JpaRepository<Item, Long> {
}
