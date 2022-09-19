package com.example.itemservice.web.controller;

import com.example.itemservice.service.ItemService;
import com.example.itemservice.web.dto.request.RequestItemInsertDto;
import com.example.itemservice.web.dto.request.RequestItemSelectDto;
import com.example.itemservice.web.dto.request.RequestItemUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public String items(@ModelAttribute("itemSearch") RequestItemSelectDto selectDto, Model model) {
        model.addAttribute("items", itemService.findItems(selectDto));
        return "items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        model.addAttribute("item", itemService.findById(itemId));
        return "item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute("item") RequestItemInsertDto insertDto, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("itemId", itemService.save(insertDto));
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        model.addAttribute("item", itemService.findById(itemId));
        return "editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute RequestItemUpdateDto updateDto) {
        itemService.update(itemId, updateDto);
        return "redirect:/items/{itemId}";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long itemId) {
        itemService.delete(itemId);
        return "redirect:/items";
    }

}
