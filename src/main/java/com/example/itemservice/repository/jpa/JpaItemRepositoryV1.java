package com.example.itemservice.repository.jpa;

import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import com.example.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional // jpa 모든 데이터 변경은 트랜젝션 안에서 이루어짐
@RequiredArgsConstructor
public class JpaItemRepositoryV1 implements ItemRepository {
    private final EntityManager em;

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public void update(Item item) {
        Item findItem = em.find(Item.class, item.getId());
        findItem.setItemName(item.getItemName());
        findItem.setPrice(item.getPrice());
        findItem.setQuantity(item.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll(ItemSearchCondition condition) {
        String jpql = "select i from Item i";

        Integer maxPrice = condition.getMaxPrice();
        String itemName = condition.getItemName();

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            jpql += " where";
        }
        boolean andFlag = false;

        List<Object> param = new ArrayList<>();
        if (StringUtils.hasText(itemName)) {
            jpql += " i.itemName like concat('%',:itemName,'%')";
            param.add(itemName);
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                jpql += " and";
            }

            jpql += " i.price <= :maxPrice";
            param.add(maxPrice);
        }

        log.info("jpql={}", jpql);

        TypedQuery<Item> query = em.createQuery(jpql, Item.class);

        if (StringUtils.hasText(itemName)) {
            query.setParameter("itemName", itemName);
        }

        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }

        return query.getResultList();
    }

    @Override
    public void delete(Long itemId) {
        Item findItem = em.find(Item.class, itemId);
        em.remove(findItem);
    }
}
