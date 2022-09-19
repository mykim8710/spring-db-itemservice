package com.example.itemservice;

import com.example.itemservice.domain.Item;
import com.example.itemservice.repository.ItemRepository;
import com.example.itemservice.web.dto.request.RequestItemInsertDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");

        Item itemA = Item.makeSaveModel(new RequestItemInsertDto("itemA", 10000, 10));
        Item itemB = Item.makeSaveModel(new RequestItemInsertDto("itemB", 20000, 20));

        itemRepository.save(itemA);
        itemRepository.save(itemB);
    }

}
