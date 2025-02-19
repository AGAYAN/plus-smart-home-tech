package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.order.Dto.AddressDto;
import ru.yandex.practicum.order.Dto.ProductReturnRequest;
import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.service.WarehouseService;
import ru.yandex.practicum.warehouse.controller.WarehouseClient;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseDto;

@RestController
@RequiredArgsConstructor
public class WarehouseController implements WarehouseClient {

    private final WarehouseService warehouseService;

    @Override
    public void addNewProduct(NewProductInWarehouseDto request) {
        warehouseService.addNewProduct(request);
    }

    @Override
    public ResponseEntity<Object> checkProductAvailability(@RequestBody @Valid ShoppingCartDto shoppingCart) {
        try {
            BookedProductsDto response = warehouseService.checkAvailableProducts(shoppingCart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @Override
    public ResponseEntity<Void> createProductToWarehouse(@RequestBody @Valid NewProductInWarehouseDto request) {
        warehouseService.createProductToWarehouse(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<AddressDto> getAddress() {
        warehouseService.addAddress();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> increaseProductQuantity(AddProductToWarehouseRequest request) {
        warehouseService.increaseProductQuantity(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<BookedProductsDto> assemblyProducts(@RequestBody ProductReturnRequest productReturnRequest) {
        warehouseService.addProducts(productReturnRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
} 
