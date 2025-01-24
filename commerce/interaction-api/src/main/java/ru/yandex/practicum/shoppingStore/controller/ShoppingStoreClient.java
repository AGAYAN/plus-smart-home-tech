package ru.yandex.practicum.shoppingStore.controller;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shoppingStore.dto.ProductDto;
import ru.yandex.practicum.shoppingStore.dto.SetProductQuantityStateDto;


import java.util.List;

@FeignClient(name = "shopping-store")
public interface ShoppingStoreClient {

    @PutMapping("/api/v1/shopping-store")
    ResponseEntity<Object> createProduct(@RequestBody ProductDto productDto);

    @GetMapping("/api/v1/shopping-store/{productId}")
    ResponseEntity<Object> ProductIdInformation(@PathVariable Long productId);

    @GetMapping("/api/v1/shopping-store")
    ResponseEntity<List<ProductDto>> getProductByCategory(@RequestParam("category") String category, SpringDataWebProperties.Pageable pageable);

    @PostMapping("/api/v1/shopping-store")
    ResponseEntity<Object> updateProduct(@RequestBody ProductDto productDto);

    @PostMapping("/api/v1/shopping-store/removeProductFromStore")
    ResponseEntity<Object> deleteProductFromStore(@RequestParam("productId") Long productId);

    @PostMapping("/api/v1/shopping-store/quantityState")
    ResponseEntity<Object> addProducrQuantityState(@RequestBody SetProductQuantityStateDto setProductQuantityStateDto);
}
