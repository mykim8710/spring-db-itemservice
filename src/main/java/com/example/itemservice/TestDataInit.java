package com.example.itemservice;

import com.example.itemservice.domain.Item;
import com.example.itemservice.repository.jpav2.ItemRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    //private final ItemRepository itemRepository;
    private final ItemRepositoryV2 itemRepository;  //v2 init

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");

        Item itemA = Item.builder()
                            .itemName("itemA")
                            .price(1000)
                            .quantity(10)
                            .build();

        Item itemB = Item.builder()
                            .itemName("itemB")
                            .price(2000)
                            .quantity(20)
                            .build();

        itemRepository.save(itemA);
        itemRepository.save(itemB);
    }

}
