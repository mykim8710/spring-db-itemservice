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
    public Long save(RequestItemInsertDto insertDto) {
        Item saveItem = Item.makeSaveModel(insertDto);
        return itemRepository.save(saveItem).getId();
    }

    @Override
    public void update(Long itemId, RequestItemUpdateDto updateDto) {
        Item updateItem = Item.makeUpdateModel(itemId, updateDto);
        itemRepository.update(updateItem);
    }

    @Override
    public ResponseItemSelectDto findById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found this item"));
        return item.toResponseItemSelectDto();
    }

    @Override
    public List<ResponseItemSelectDto> findItems(RequestItemSelectDto selectDto) {
        List<ResponseItemSelectDto> responseItemSelectDtos = new ArrayList<>();
        List<Item> items = itemRepository.findAll(ItemSearchCondition.makeItemSearchCondition(selectDto));

        if(items.size() > 0){
            responseItemSelectDtos.addAll(items
                                            .stream()
                                            .map(Item -> Item.toResponseItemSelectDto())
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
