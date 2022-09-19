package com.example.itemservice.service;

import com.example.itemservice.web.dto.request.RequestItemInsertDto;
import com.example.itemservice.web.dto.request.RequestItemSelectDto;
import com.example.itemservice.web.dto.request.RequestItemUpdateDto;
import com.example.itemservice.web.dto.response.ResponseItemSelectDto;

import java.util.List;

public interface ItemService {

    Long save(RequestItemInsertDto insertDto);

    void update(Long itemId, RequestItemUpdateDto updateDto);

    ResponseItemSelectDto findById(Long id);

    List<ResponseItemSelectDto> findItems(RequestItemSelectDto selectDto);

    void delete(Long itemId);
}
