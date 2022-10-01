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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceV1 implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public Long save(RequestItemInsertDto dto) {
        Item item = Item.builder()
                            .itemName(dto.getItemName())
                            .price(dto.getPrice())
                            .quantity(dto.getQuantity())
                            .build();
        return itemRepository.save(item).getId();
    }

    @Override
    @Transactional
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
        List<Item> items = itemRepository.findAll(ItemSearchCondition.builder()
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
    @Transactional
    public void delete(Long itemId) {
        itemRepository.delete(itemId);
    }
}
