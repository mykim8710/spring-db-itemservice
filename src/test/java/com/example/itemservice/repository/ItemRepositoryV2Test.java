package com.example.itemservice.repository;

import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import com.example.itemservice.repository.v2.ItemQuerydslRepositoryV2;
import com.example.itemservice.repository.v2.ItemRepositoryV2;
import com.example.itemservice.web.dto.request.RequestItemUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ItemRepositoryV2Test {

    @Autowired
    ItemQuerydslRepositoryV2 itemQuerydslRepositoryV2;
    @Autowired
    ItemRepositoryV2 itemRepositoryV2;

    @Test
    void save() {
        // given
        Item item = Item.builder()
                            .itemName("itemA")
                            .price(1000)
                            .quantity(10)
                            .build();

        // when
        Item savedItem = itemRepositoryV2.save(item);

        // then
        Item findItem = itemRepositoryV2.findById(savedItem.getId()).get();
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void updateItem() {
        //given
        Item saveItem = Item.builder()
                                .itemName("itemA")
                                .price(1000)
                                .quantity(10)
                                .build();

        Item savedItem = itemRepositoryV2.save(saveItem);
        Long itemId = savedItem.getId();

        //when
        RequestItemUpdateDto dto =  new RequestItemUpdateDto("itemB", 20000, 20);

        // then
        Item findItem = itemRepositoryV2.findById(itemId).get();
        findItem.setItemName(dto.getItemName());
        findItem.setPrice(dto.getPrice());
        findItem.setQuantity(dto.getQuantity());

        assertThat(findItem.getItemName()).isEqualTo(dto.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(dto.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(dto.getQuantity());
    }

    @Test
    void findItems() {
        //given
        Item item1 = Item.builder()
                            .itemName("itemA-1")
                            .price(10000)
                            .quantity(10)
                            .build();

        Item item2 = Item.builder()
                            .itemName("itemA-2")
                            .price(20000)
                            .quantity(20)
                            .build();

        Item item3 = Item.builder()
                            .itemName("itemB-1")
                            .price(30000)
                            .quantity(30)
                            .build();

        itemRepositoryV2.save(item1);
        itemRepositoryV2.save(item2);
        itemRepositoryV2.save(item3);

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
        List<Item> result = itemQuerydslRepositoryV2.findAll(new ItemSearchCondition(itemName, maxPrice));
        System.out.println("result = " + result);
        assertThat(result).containsExactly(items);  // 객체 비교를 할 때에는 참조 주소로 비교, @EqualsAndHashCode로 해결
    }
}
