package com.example.itemservice.repository.jdbctemplate;


import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import com.example.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcTemplateItemRepositoryV1 implements ItemRepository {
    private final JdbcTemplate template;

    public JdbcTemplateItemRepositoryV1(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item insertItem) {
        String sql = "insert into item(item_name, price, quantity) values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            // 자동 증가키
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, insertItem.getItemName());
            pstmt.setInt(2, insertItem.getPrice());
            pstmt.setInt(3, insertItem.getQuantity());
            return pstmt;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        insertItem.setId(key);

        return insertItem;
    }

    @Override
    public void update(Item updateItem) {
        String sql = "update item set item_name=?, price=?, quantity=? where id=?";

        template.update(sql,updateItem.getItemName(),
                            updateItem.getPrice(),
                            updateItem.getQuantity(),
                            updateItem.getId());
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name, price, quantity from item where id = ?";

        try {
            Item item = template.queryForObject(sql, itemRowMapper(), id);
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


        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }

        boolean andFlag = false;

        List<Object> param = new ArrayList<>();

        // 동적쿼리
        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',?,'%')";
            param.add(itemName);
            andFlag = true;
        }

        if(maxPrice != null) {
            if(andFlag) {
                sql += " and";
            }

            sql += " price <= ?";
            param.add(maxPrice);
        }

        log.info("sql={}", sql);

        return template.query(sql, itemRowMapper(), param.toArray());
    }

    @Override
    public void delete(Long itemId) {
        String sql = "delete from item where id = ?";
        template.update(sql, itemId);
    }

    private RowMapper<Item> itemRowMapper() {
        return (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setItemName(rs.getString("item_name"));
            item.setPrice(rs.getInt("price"));
            item.setQuantity(rs.getInt("quantity"));
            return item;
        }; }
}
