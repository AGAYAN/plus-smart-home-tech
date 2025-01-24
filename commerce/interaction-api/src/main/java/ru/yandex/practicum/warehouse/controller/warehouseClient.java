package ru.yandex.practicum.warehouse.controller;


import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseDto;

@FeignClient(name = "warehouse")
public interface warehouseClient {

    @PostMapping("/api/v1/warehouse/check")
    ResponseEntity<Object> checkProductAvailability(@RequestBody @Valid ShoppingCartDto shoppingCart);

    @PostMapping("/api/v1/warehouse")
    ResponseEntity<Void> createProductToWarehouse(@RequestBody @Valid NewProductInWarehouseDto request);
}
