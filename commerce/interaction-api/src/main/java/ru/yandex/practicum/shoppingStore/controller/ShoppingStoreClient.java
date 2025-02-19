package ru.yandex.practicum.shoppingStore.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shoppingStore.dto.Pageable;
import ru.yandex.practicum.shoppingStore.dto.ProductDto;
import ru.yandex.practicum.shoppingStore.dto.SetProductQuantityStateDto;


import java.util.List;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShoppingStoreClient {

    @PutMapping()
    ResponseEntity<Object> createProduct(@RequestBody ProductDto productDto);

    @GetMapping("/{productId}")
    ResponseEntity<Object> ProductIdInformation(@PathVariable Long productId);

    @GetMapping()
    ResponseEntity<List<ProductDto>> getProductByCategory(@RequestParam("category") String category, Pageable pageable);

    @PostMapping()
    ResponseEntity<Object> updateProduct(@RequestBody ProductDto productDto);

    @PostMapping("/removeProductFromStore")
    ResponseEntity<Object> deleteProductFromStore(@RequestParam("productId") Long productId);

    @PostMapping("/quantityState")
    ResponseEntity<Object> addProducrQuantityState(@RequestBody SetProductQuantityStateDto setProductQuantityStateDto);
}
