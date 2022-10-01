package com.example.itemservice.repository.jpav2;

import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.itemservice.domain.QItem.item;

@Repository
public class ItemQuerydslRepositoryV2 {
    private final JPAQueryFactory query; // querydsl

    public ItemQuerydslRepositoryV2(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Item> findAll(ItemSearchCondition condition) {
        String itemName = condition.getItemName();
        Integer maxPrice = condition.getMaxPrice();

        return query
                .select(item)
                .from(item)
                .where(likeItemName(itemName),
                       maxPrice(maxPrice))
                .fetch();
    }

    private BooleanExpression likeItemName(String itemName) {
        if (StringUtils.hasText(itemName)) {
            return item.itemName.like("%" + itemName + "%");
        }
        return null;
    }
    private BooleanExpression maxPrice(Integer maxPrice) {
        if (maxPrice != null) {
            return item.price.loe(maxPrice);
        }
        return null;
    }
}
