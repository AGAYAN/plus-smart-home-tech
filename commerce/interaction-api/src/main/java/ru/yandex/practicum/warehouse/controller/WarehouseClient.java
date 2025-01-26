package ru.yandex.practicum.warehouse.controller;


import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseDto;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient {

    @PostMapping("/check")
    ResponseEntity<Object> checkProductAvailability(@RequestBody @Valid ShoppingCartDto shoppingCart);

    @PostMapping("/warehouse")
    ResponseEntity<Void> createProductToWarehouse(@RequestBody @Valid NewProductInWarehouseDto request);

    @GetMapping("/address")
    ResponseEntity<AddressDto> getAddress();

    @PostMapping("/add")
    ResponseEntity<Void> increaseProductQuantity(@RequestBody @Valid AddProductToWarehouseRequest request);
}
