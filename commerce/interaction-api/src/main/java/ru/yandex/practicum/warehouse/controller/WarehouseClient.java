package ru.yandex.practicum.warehouse.controller;


import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.order.Dto.AddressDto;
import ru.yandex.practicum.order.Dto.ProductReturnRequest;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseDto;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient {

    @PostMapping("/check")
    ResponseEntity<Object> checkProductAvailability(@RequestBody @Valid ShoppingCartDto shoppingCart);

    @GetMapping("/address")
    ResponseEntity<AddressDto> getAddress();

    @PostMapping("/warehouse")
    ResponseEntity<Void> createProductToWarehouse(@RequestBody @Valid NewProductInWarehouseDto request);

    @PostMapping("/add")
    ResponseEntity<Void> increaseProductQuantity(@RequestBody @Valid AddProductToWarehouseRequest request);

    @PostMapping("/assembly")
    ResponseEntity<BookedProductsDto> assemblyProducts(@RequestBody ProductReturnRequest productReturnRequest);
}
