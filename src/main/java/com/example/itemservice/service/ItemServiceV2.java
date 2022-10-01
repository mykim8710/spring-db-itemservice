package com.example.itemservice.service;

import com.example.itemservice.domain.Item;
import com.example.itemservice.domain.ItemSearchCondition;
import com.example.itemservice.excepetion.NotFoundException;
import com.example.itemservice.repository.v2.ItemQuerydslRepositoryV2;
import com.example.itemservice.repository.v2.ItemRepositoryV2;
import com.example.itemservice.web.dto.request.RequestItemInsertDto;
import com.example.itemservice.web.dto.request.RequestItemSelectDto;
import com.example.itemservice.web.dto.request.RequestItemUpdateDto;
import com.example.itemservice.web.dto.response.ResponseItemSelectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceV2 implements ItemService {
    private final ItemRepositoryV2 itemRepositoryV2;
    private final ItemQuerydslRepositoryV2 itemQuerydslRepositoryV2;

    @Override
    public Long save(RequestItemInsertDto dto) {
        Item item = Item.builder()
                            .itemName(dto.getItemName())
                            .price(dto.getPrice())
                            .quantity(dto.getQuantity())
                            .build();
        return itemRepositoryV2.save(item).getId();
    }

    @Override
    public void update(Long itemId, RequestItemUpdateDto dto) {
        Item findItem = itemRepositoryV2.findById(itemId).orElseThrow();
        findItem.setItemName(dto.getItemName());
        findItem.setPrice(dto.getPrice());
        findItem.setQuantity(dto.getQuantity());
    }

    @Override
    public ResponseItemSelectDto findById(Long id) {
        Item item = itemRepositoryV2.findById(id).orElseThrow(() -> new NotFoundException("Not Found this item"));
        return ResponseItemSelectDto.builder()
                                        .id(item.getId())
                                        .itemName(item.getItemName())
                                        .price(item.getPrice())
                                        .quantity(item.getQuantity())
                                        .build();
    }

    @Override
    public List<ResponseItemSelectDto> findItems(RequestItemSelectDto selectDto) {
        List<Item> items = itemQuerydslRepositoryV2.findAll(ItemSearchCondition.builder()
                                                                                .itemName(selectDto.getItemName())
                                                                                .maxPrice(selectDto.getMaxPrice())
                                                                                .build());

        List<ResponseItemSelectDto> responseItemSelectDtos = new ArrayList<>();
        if(items.size() > 0){
            responseItemSelectDtos.addAll(items
                                            .stream()
                                            .map(Item -> ResponseItemSelectDto.builder()
                                                                                .id(Item.getId())
                                                                                .itemName(Item.getItemName())
                                                                                .price(Item.getPrice())
                                                                                .quantity(Item.getQuantity())
                                                                                .build())
                                            .collect(Collectors.toList())
            );
        }

        return responseItemSelectDtos;
    }

    @Override
    public void delete(Long itemId) {
        itemRepositoryV2.deleteById(itemId);
    }
}
