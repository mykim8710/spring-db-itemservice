package com.example.itemservice.repository;

import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import com.example.itemservice.repository.ItemRepository;
import com.example.itemservice.repository.MemoryItemRepository;
import com.example.itemservice.web.dto.request.RequestItemInsertDto;
import com.example.itemservice.web.dto.request.RequestItemUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @AfterEach
    void afterEach() {
        //MemoryItemRepository의 경우 제한적으로 사용
        if (itemRepository instanceof MemoryItemRepository) {
            ((MemoryItemRepository) itemRepository).clearStore();
        }
    }

    @Test
    void save() {
        // given
        RequestItemInsertDto insertDto = new RequestItemInsertDto("itemA", 10000, 10);
        Item saveItem = Item.makeSaveModel(insertDto);

        // when
        Item savedItem = itemRepository.save(saveItem);

        // then
        Item findItem = itemRepository.findById(savedItem.getId()).get();
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void updateItem() {
        //given
        RequestItemInsertDto insertDto = new RequestItemInsertDto("itemA", 10000, 10);
        Item saveItem = Item.makeSaveModel(insertDto);
        Item savedItem = itemRepository.save(saveItem);
        Long itemId = savedItem.getId();

        //when
        RequestItemUpdateDto updateDto = new RequestItemUpdateDto("item2", 20000, 30);
        Item updateItem = Item.makeUpdateModel(itemId, updateDto);
        itemRepository.update(updateItem);

        // then
        Item findItem = itemRepository.findById(itemId).get();
        assertThat(findItem.getItemName()).isEqualTo(updateDto.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateDto.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateDto.getQuantity());
    }

    @Test
    void findItems() {
        //given
        RequestItemInsertDto insertDto1 = new RequestItemInsertDto("itemA-1", 10000, 10);
        Item item1 = Item.makeSaveModel(insertDto1);

        RequestItemInsertDto insertDto2 = new RequestItemInsertDto("itemA-2", 20000, 20);
        Item item2 = Item.makeSaveModel(insertDto2);

        RequestItemInsertDto insertDto3 = new RequestItemInsertDto("itemB-1", 30000, 30);
        Item item3 = Item.makeSaveModel(insertDto3);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        //둘 다 없음 검증
        findItemSearch(null, null, item1, item2, item3);
        findItemSearch("", null, item1, item2, item3);

        //itemName 검증
        findItemSearch("itemA", null, item1, item2);
        findItemSearch("temA", null, item1, item2);
        findItemSearch("itemB", null, item3);

        //maxPrice 검증
        findItemSearch(null, 10000, item1);

        //둘 다 있음 검증
        findItemSearch("itemA", 10000, item1);
    }

    void findItemSearch(String itemName, Integer maxPrice, Item... items) {
        List<Item> result = itemRepository.findAll(new ItemSearchCondition(itemName, maxPrice));
        assertThat(result).containsExactly(items);
    }
}
