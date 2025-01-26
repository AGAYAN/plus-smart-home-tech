package ru.yandex.practicum.shoppingCart.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shoppingCart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;

import java.util.Map;

@FeignClient(name = "shopping-cart", path = "/api/v1/shopping-cart")
public interface ShoppingCartClient {

    @PostMapping()
    ResponseEntity<ShoppingCartDto> addProductsToCart(
            @RequestParam String username,
            @RequestBody Map<String, Integer> products);

    @GetMapping()
    ResponseEntity<ShoppingCartDto> getShoppingCart(@RequestParam String username);

    @DeleteMapping()
    ResponseEntity<String> deactivateShoppingCart(@RequestParam String username);

    @PutMapping("/remove")
    ResponseEntity<ShoppingCartDto> updateCart(
            @RequestParam String username,
            @RequestBody Map<String, Integer> products);

    @PostMapping("/change-quantity")
    ResponseEntity<Object> changeProductQuantity(
            @RequestParam String username,
            @RequestBody ChangeProductQuantityRequest request);

    @PostMapping("/booking")
    ResponseEntity<Object> reserveProducts(@RequestParam String username);
}
