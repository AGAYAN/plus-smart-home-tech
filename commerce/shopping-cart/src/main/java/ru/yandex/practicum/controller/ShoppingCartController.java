package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.exception.ErrorResponse;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.service.ShoppingCartService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping
    public ResponseEntity<ShoppingCartDto> addProductsToCart(
            @RequestParam String username,
            @RequestBody Map<String, Integer> products) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ShoppingCartDto("Имя пользователя не должно быть пустым.", null));
        }

        try {
            ShoppingCartDto response = shoppingCartService.addProductToCart(username, products);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ShoppingCartDto(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ShoppingCartDto> getShoppingCart(@RequestParam String username) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ShoppingCartDto("Имя пользователя не должно быть пустым.", null));
        }

        ShoppingCartDto response = shoppingCartService.getShoppingCart(username);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping
    public ResponseEntity<String> deactivateShoppingCart(@RequestParam String username) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Имя пользователя не должно быть пустым.");
        }

        shoppingCartService.deactivateShoppingCart(username);
        return ResponseEntity.ok("Корзина пользователя успешно деактивирована.");
    }

    @PutMapping("/remove")
    public ResponseEntity<ShoppingCart> updateCart(
            @RequestParam String username,
            @RequestBody Map<String, Integer> products) {

        try {
            ShoppingCart updatedCart = shoppingCartService.updateCart(username, products);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/change-quantity")
    public ResponseEntity<Object> changeProductQuantity(
            @RequestParam String username,
            @RequestBody ChangeProductQuantityRequest request) {
        try {
            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new RuntimeException("Имя пользователя не должно быть пустым"));
            }

            ShoppingCartDto updatedProduct = shoppingCartService.changeProductQuantity(username, request);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);


        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/booking")
    public ResponseEntity<Object> reserveProducts(@RequestParam String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new RuntimeException("Имя пользователя не должно быть пустым"));
        }
        try {
            BookedProductsDto bookingResponse = shoppingCartService.reserveItems(username);
            return ResponseEntity.ok(bookingResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Ошибка, товара уже нет на складе"));
        }
    }
}
