package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.NewProductInWarehouseDto;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.service.WarehouseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("/check")
    public ResponseEntity<Object> checkProductAvailability(@RequestBody @Valid ShoppingCartDto shoppingCart) {
        try {
            BookedProductsDto response = warehouseService.checkAvailableProducts(shoppingCart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createProductToWarehouse(@RequestBody @Valid NewProductInWarehouseDto request) {
        warehouseService.createProductToWarehouse(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
