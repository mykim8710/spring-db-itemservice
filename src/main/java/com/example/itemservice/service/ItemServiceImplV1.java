package com.example.itemservice.service;

import com.example.itemservice.domain.ItemSearchCondition;
import com.example.itemservice.excepetion.NotFoundException;
import com.example.itemservice.repository.ItemRepository;
import com.example.itemservice.web.dto.request.RequestItemInsertDto;
import com.example.itemservice.web.dto.request.RequestItemSelectDto;
import com.example.itemservice.web.dto.request.RequestItemUpdateDto;
import com.example.itemservice.domain.Item;
import com.example.itemservice.web.dto.response.ResponseItemSelectDto;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Service
@RequiredArgsConstructor
public class ItemServiceImplV1 implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Long save(RequestItemInsertDto dto) {
        Item item = Item.builder()
                            .itemName(dto.getItemName())
                            .price(dto.getPrice())
                            .quantity(dto.getQuantity())
                            .build();
        return itemRepository.save(item).getId();
    }

    @Override
    public void update(Long itemId, RequestItemUpdateDto dto) {
        Item item = Item.builder()
                            .id(itemId)
                            .itemName(dto.getItemName())
                            .price(dto.getPrice())
                            .quantity(dto.getQuantity())
                            .build();
        itemRepository.update(item);
    }

    @Override
    public ResponseItemSelectDto findById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found this item"));

        return ResponseItemSelectDto.builder()
                                        .id(item.getId())
                                        .itemName(item.getItemName())
                                        .price(item.getPrice())
                                        .quantity(item.getQuantity())
                                        .build();
    }

    @Override
    public List<ResponseItemSelectDto> findItems(RequestItemSelectDto selectDto) {
        List<ResponseItemSelectDto> responseItemSelectDtos = new ArrayList<>();
        List<Item> items = itemRepository.findAll(ItemSearchCondition.makeItemSearchCondition(selectDto));

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
        itemRepository.delete(itemId);
    }
}
