package ru.yandex.practicum.shoppingCart.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shoppingCart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;

import java.util.Map;

@FeignClient(name = "shopping-cart")
public interface ShoppingCartClient {

    @PostMapping("/api/v1/shopping-cart")
    ResponseEntity<ShoppingCartDto> addProductsToCart(
            @RequestParam String username,
            @RequestBody Map<String, Integer> products);

    @GetMapping("/api/v1/shopping-cart")
    ResponseEntity<ShoppingCartDto> getShoppingCart(@RequestParam String username);

    @DeleteMapping("/api/v1/shopping-cart")
    ResponseEntity<String> deactivateShoppingCart(@RequestParam String username);

    @PutMapping("/api/v1/shopping-cart/remove")
    ResponseEntity<ShoppingCartDto> updateCart(
            @RequestParam String username,
            @RequestBody Map<String, Integer> products);

    @PostMapping("/api/v1/shopping-cart/change-quantity")
    ResponseEntity<Object> changeProductQuantity(
            @RequestParam String username,
            @RequestBody ChangeProductQuantityRequest request);

    @PostMapping("/api/v1/shopping-cart/booking")
    ResponseEntity<Object> reserveProducts(@RequestParam String username);
}
