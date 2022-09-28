package com.example.itemservice.repository;

import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import com.example.itemservice.repository.memory.MemoryItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@Commit
@Transactional
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    // 트랜젝션 관련 코드
    /*@Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;

    @BeforeEach
    void beforeEach() {
        // 트랜젝션 시작
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    } */


    // @Rollback(value=false)
    @Test
    void save() {
        // given
        Item item = Item.builder()
                            .itemName("itemA")
                            .price(1000)
                            .quantity(10)
                            .build();

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(savedItem.getId()).get();
        assertThat(findItem).isEqualTo(savedItem);  // 객체 비교를 할 때에는 참조 주소로 비교, @EqualsAndHashCode로 해결
    }

    @AfterEach
    void afterEach() {
        //MemoryItemRepository의 경우 제한적으로 사용
        if (itemRepository instanceof MemoryItemRepository) {
            ((MemoryItemRepository) itemRepository).clearStore();
        }

        // 트랜젝션 롤백
        //transactionManager.rollback(status);
    }

    @Test
    void updateItem() {
        //given
        Item saveItem = Item.builder()
                                .itemName("itemA")
                                .price(1000)
                                .quantity(10)
                                .build();
        Item savedItem = itemRepository.save(saveItem);
        Long itemId = savedItem.getId();

        //when
        Item updateItem = Item.builder()
                                .id(itemId)
                                .itemName("itemB")
                                .price(20000)
                                .quantity(20)
                                .build();

        itemRepository.update(updateItem);

        // then
        Item findItem = itemRepository.findById(itemId).get();

        assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateItem.getQuantity());
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
        System.out.println("result = " + result);
        assertThat(result).containsExactly(items);  // 객체 비교를 할 때에는 참조 주소로 비교, @EqualsAndHashCode로 해결
    }
}
