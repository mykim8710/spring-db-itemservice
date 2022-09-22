package com.example.itemservice.repository.jdbctemplate;


import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import com.example.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcTemplateItemRepositoryV2 implements ItemRepository {
    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateItemRepositoryV2(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item insertItem) {
        String sql = "insert into item(item_name, price, quantity) values (:itemName, :price, :quantity)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(insertItem);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        long key = keyHolder.getKey().longValue();
        insertItem.setId(key);

        return insertItem;
    }

    @Override
    public void update(Item updateItem) {
        String sql = "update item set item_name=:itemName, price=:price, quantity=:quantity where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                                            .addValue("itemName", updateItem.getItemName())
                                            .addValue("price", updateItem.getPrice())
                                            .addValue("quantity", updateItem.getQuantity())
                                            .addValue("id", updateItem.getId()); //이 부분이 별도로 필요하다.
        template.update(sql, param);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name, price, quantity from item where id = :id";

        try {
            Map<String, Object> param = Map.of("id", id);
            Item item = template.queryForObject(sql, param, itemRowMapper());
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCondition condition) {
        String sql = "select id, item_name, price, quantity from item";

        String itemName = condition.getItemName();
        Integer maxPrice = condition.getMaxPrice();

        SqlParameterSource param = new BeanPropertySqlParameterSource(condition);

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }

        boolean andFlag = false;

        // 동적쿼리
        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',:itemName,'%')";
            andFlag = true;
        }

        if(maxPrice != null) {
            if(andFlag) {
                sql += " and";
            }

            sql += " price <= :maxPrice";
        }

        log.info("sql={}", sql);
        return template.query(sql, param, itemRowMapper());
    }

    @Override
    public void delete(Long itemId) {
        String sql = "delete from item where id = :id";
        Map<String, Object> param = Map.of("id", itemId);
        template.update(sql, param);
    }

    private RowMapper<Item> itemRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class);
    }
}
